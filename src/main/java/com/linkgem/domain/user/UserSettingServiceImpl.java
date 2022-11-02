package com.linkgem.domain.user;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.linkgem.domain.common.file.Directory;
import com.linkgem.domain.common.file.FileCommand;
import com.linkgem.domain.common.file.FileInfo;
import com.linkgem.domain.common.file.FileStore;
import com.linkgem.domain.gembox.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxService;
import com.linkgem.infrastructure.common.aws.S3ObjectKeyCreator;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import com.linkgem.presentation.user.dto.UserRequest.AddDetailInfoRequest;
import com.linkgem.presentation.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSettingServiceImpl implements UserSettingService {

	private final GemBoxService gemBoxService;
	private final UserReader userReader;
	private final FileStore fileStore;

    private final S3ObjectKeyCreator s3ObjectKeyCreator;

	@Override
	@Transactional
	public void addDetailInfo(Long userId, AddDetailInfoRequest addDetailInfoRequest) {

		if (addDetailInfoRequest.getUserNickname().isBlank()) {
			throw new BusinessException(ErrorCode.USER_NICKNAME_NOT_VALID);
		} else if (userReader.existsByNickname(addDetailInfoRequest.getUserNickname())) {
			throw new BusinessException(ErrorCode.USER_NICKNAME_ALREADY_EXIST);
		} else if (Objects.isNull(addDetailInfoRequest.getCareerYear())
			|| addDetailInfoRequest.getCareerYear() < 0) {
			throw new BusinessException(ErrorCode.CAREER_YEAR_NOT_VALID);
		} else if (addDetailInfoRequest.getJobName().isBlank()) {
			throw new BusinessException(ErrorCode.JOB_NOT_VALID);
		}
		User user = userReader.find(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		user.updateCareerYear(addDetailInfoRequest.getCareerYear());
		user.updateJob(addDetailInfoRequest.getJobName());
		user.updateNickname(addDetailInfoRequest.getUserNickname());
		user.updateUserPhaseRegistered();

	}

	@Override
	@Transactional
	public UserResponse.SettingResponse settingUserInfo(Long userId, MultipartFile profileImage, String nickName,
		String jobName,
		Integer careerYear) {
		if (Objects.isNull(careerYear)
			|| careerYear < 0) {
			throw new BusinessException(ErrorCode.CAREER_YEAR_NOT_VALID);
		} else if (jobName.isBlank()) {
			throw new BusinessException(ErrorCode.JOB_NOT_VALID);
		}
		User user = userReader.find(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		if (Objects.nonNull(nickName)) {
			if (userReader.existsByNickname(nickName)) {
				if (!Objects.equals(userReader.findByNickname(nickName).get().getId(), userId)) {
					throw new BusinessException(ErrorCode.USER_NICKNAME_ALREADY_EXIST);
				}
			} else {
				user.updateNickname(nickName);
			}
		}
		user.updateCareerYear(careerYear);
		user.updateJob(jobName);

		//기본 잼박스 생성
		gemBoxService.create(GemBoxCommand.Create.createDefault(userId));

		if (Objects.isNull(profileImage)) {
			return UserResponse.SettingResponse.of(userId, nickName, jobName, careerYear, null);
		}

		if (Objects.nonNull(user.getProfileImageUrl())) {
			if (!user.getProfileImageUrl().contains("DEFAULT_USERPROFILE")) {
				fileStore.delete(FileCommand.DeleteFile.of(user.getProfileImageUrl()));
			}
		}

		//유저 프로필 이미지 생성
        final String objectKey =
            s3ObjectKeyCreator.create(Directory.USER_PROFILE, userId);
        FileCommand.UploadFile uploadFile = FileCommand.UploadFile.of(profileImage, objectKey);

		FileInfo fileInfo = fileStore.store(uploadFile);
		user.updateProfileImageUrl(fileInfo.getUrl());
		return UserResponse.SettingResponse.of(userId, nickName, jobName, careerYear, fileInfo.getUrl());

	}
}
