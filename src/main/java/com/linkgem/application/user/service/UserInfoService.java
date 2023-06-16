package com.linkgem.application.user.service;

import com.linkgem.presentation.user.dto.UserResponse;

public interface UserInfoService {

    UserResponse.UserInfoResponse info(Long userId);
}
