package com.linkgem.domain.user;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.linkgem.domain.common.file.Directory;
import com.linkgem.domain.common.file.FileCommand;
import com.linkgem.domain.common.file.FileInfo;
import com.linkgem.domain.common.file.FileStore;
import com.linkgem.infrastructure.user.UserRepository;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import com.linkgem.presentation.user.dto.UserRequest;
import com.linkgem.presentation.user.dto.UserRequest.AddDetailInfoRequest;
import com.linkgem.presentation.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final FileStore fileStore;

	@Override
	@Transactional
	public void addDetailInfo(Long userId, AddDetailInfoRequest addDetailInfoRequest) {

		if (addDetailInfoRequest.getUserNickname().isBlank()) {
			throw new BusinessException(ErrorCode.USER_NICKNAME_NOT_VALID);
		} else if (userRepository.existsByNickname(addDetailInfoRequest.getUserNickname())) {
			throw new BusinessException(ErrorCode.USER_NICKNAME_ALREADY_EXIST);
		} else if (Objects.isNull(addDetailInfoRequest.getCareerYear())
			|| addDetailInfoRequest.getCareerYear() < 0) {
			throw new BusinessException(ErrorCode.CAREER_YEAR_NOT_VALID);
		} else if (addDetailInfoRequest.getJobName().isBlank()) {
			throw new BusinessException(ErrorCode.JOB_NOT_VALID);
		}
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		user.updateCareerYear(addDetailInfoRequest.getCareerYear());
		user.updateJob(addDetailInfoRequest.getJobName());
		user.updateNickname(addDetailInfoRequest.getUserNickname());
		user.updateUserPhaseRegistered();

	}

	@Override
	@Transactional
	public UserResponse.SettingResponse settingUserInfo(Long userId, MultipartFile profileImage, String nickName, String jobName,
		Integer careerYear) {

		if (nickName.isBlank()) {
			throw new BusinessException(ErrorCode.USER_NICKNAME_NOT_VALID);
		} else if (userRepository.existsByNickname(nickName)) {
			throw new BusinessException(ErrorCode.USER_NICKNAME_ALREADY_EXIST);
		} else if (Objects.isNull(careerYear)
			|| careerYear < 0) {
			throw new BusinessException(ErrorCode.CAREER_YEAR_NOT_VALID);
		} else if (jobName.isBlank()) {
			throw new BusinessException(ErrorCode.JOB_NOT_VALID);
		}

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		user.updateCareerYear(careerYear);
		user.updateJob(jobName);
		user.updateNickname(nickName);
		FileCommand.UploadFile uploadFile = FileCommand.UploadFile.of(profileImage,
			Directory.USER_PROFILE, userId, userId);
		FileInfo fileInfo = fileStore.store(uploadFile);
		user.updateProfileImageUrl(fileInfo.getUrl());
		return UserResponse.SettingResponse.of(userId,nickName,jobName,careerYear, fileInfo.getUrl());


	}
}
