package com.linkgem.domain.user.service;

import com.linkgem.domain.common.file.Directory;
import com.linkgem.domain.common.file.File;
import com.linkgem.domain.common.file.FileCommand;
import com.linkgem.domain.common.file.FilePersistence;
import com.linkgem.domain.gembox.dto.GemBoxCommand;
import com.linkgem.domain.gembox.service.GemBoxService;
import com.linkgem.domain.notification.dto.NotificationCommand;
import com.linkgem.domain.notification.service.NotificationService;
import com.linkgem.domain.user.persistence.UserPersistence;
import com.linkgem.domain.user.domain.User;
import com.linkgem.domain.user.provider.TokenProvider;
import com.linkgem.domain.common.file.aws.S3ObjectKeyCreator;
import com.linkgem.domain.common.exception.BusinessException;
import com.linkgem.domain.common.exception.ErrorCode;
import com.linkgem.domain.user.dto.UserRequest;
import com.linkgem.domain.user.dto.UserResponse;
import com.linkgem.domain.user.dto.UserResponse.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final NotificationService notificationService;
    private final GemBoxService gemBoxService;

    private final UserPersistence userPersistence;

    private final FilePersistence filePersistence;

    private final S3ObjectKeyCreator s3ObjectKeyCreator;

    private final TokenProvider tokenProvider;

    @Override
    public void initialize(User user) {

        final Long userId = user.getId();

        //기본 잼박스 생성
        gemBoxService.create(GemBoxCommand.Create.createDefault(userId));

        //회원가입 환영 알림
        NotificationCommand.Create joinNotification = notificationService.createJoinNotification(user);
        notificationService.create(joinNotification);
    }

    @Override
    public UserInfoResponse info(Long userId) {
        User user = userPersistence.find(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return UserInfoResponse.builder()
                .userId(user.getId())
                .loginEmail(user.getLoginEmail())
                .mailEmail(user.getMailEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .job(user.getJob())
                .careerYear(user.getCareerYear())
                .profileImageUrl(user.getProfileImageUrl())
                .isSavedFirstLink(user.getIsSavedFirstLink())
                .build();
    }

    @Override
    @Transactional
    public void addDetailInfo(Long userId, UserRequest.AddDetailInfoRequest addDetailInfoRequest) {

        if (addDetailInfoRequest.getUserNickname().isBlank()) {
            throw new BusinessException(ErrorCode.USER_NICKNAME_NOT_VALID);
        } else if (userPersistence.existsByNickname(addDetailInfoRequest.getUserNickname())) {
            throw new BusinessException(ErrorCode.USER_NICKNAME_ALREADY_EXIST);
        } else if (Objects.isNull(addDetailInfoRequest.getCareerYear())
                || addDetailInfoRequest.getCareerYear() < 0) {
            throw new BusinessException(ErrorCode.CAREER_YEAR_NOT_VALID);
        } else if (addDetailInfoRequest.getJobName().isBlank()) {
            throw new BusinessException(ErrorCode.JOB_NOT_VALID);
        }
        User user = userPersistence.find(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.updateCareerYear(addDetailInfoRequest.getCareerYear());
        user.updateJob(addDetailInfoRequest.getJobName());
        user.updateNickname(addDetailInfoRequest.getUserNickname());
        user.updateUserPhaseRegistered();

        initialize(user);
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
        User user = userPersistence.find(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        if (Objects.nonNull(nickName)) {
            if (userPersistence.existsByNickname(nickName)) {
                if (!Objects.equals(userPersistence.findByNickname(nickName).get().getId(), userId)) {
                    throw new BusinessException(ErrorCode.USER_NICKNAME_ALREADY_EXIST);
                }
            } else {
                user.updateNickname(nickName);
            }
        }
        user.updateCareerYear(careerYear);
        user.updateJob(jobName);

        if (Objects.isNull(profileImage)) {
            return UserResponse.SettingResponse.of(userId, nickName, jobName, careerYear, null);
        }

        if (Objects.nonNull(user.getProfileImageUrl())) {
            if (!user.getProfileImageUrl().contains("DEFAULT_USERPROFILE")) {
                filePersistence.delete(FileCommand.DeleteFile.of(user.getProfileImageUrl()));
            }
        }

        //유저 프로필 이미지 생성
        final String objectKey =
                s3ObjectKeyCreator.create(Directory.USER_PROFILE, userId);
        FileCommand.UploadFile uploadFile = FileCommand.UploadFile.of(profileImage, objectKey);

        File file = filePersistence.store(uploadFile);
        user.updateProfileImageUrl(file.getUrl());
        return UserResponse.SettingResponse.of(userId, nickName, jobName, careerYear, file.getUrl());
    }

    @Override
    public UserResponse.TokenReissueResponse reissue(String accessToken, String refreshToken) {
        if (tokenProvider.isExpiredAccessToken(accessToken)) {
            String userId = tokenProvider.isValidRefreshToken(refreshToken);
            String createdAccessToken = tokenProvider.createAccessToken(userId);
            return UserResponse.TokenReissueResponse.builder()
                    .accessToken(createdAccessToken)
                    .build();
        } else
            throw new BusinessException(ErrorCode.ACCESS_TOKEN_NOT_EXPIRED);
    }

}
