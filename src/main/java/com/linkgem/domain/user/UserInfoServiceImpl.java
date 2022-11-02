package com.linkgem.domain.user;

import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import com.linkgem.presentation.user.dto.UserResponse.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService{

    private final UserReader userReader;
    @Override
    public UserInfoResponse info(Long userId) {
        User user = userReader.find(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return UserInfoResponse.builder()
                .userId(user.getId())
                .loginEmail(user.getLoginEmail())
                .mailEmail(user.getMailEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .job(user.getJob())
                .careerYear(user.getCareerYear())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
