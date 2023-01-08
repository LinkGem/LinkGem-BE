package com.linkgem.domain.user;

import com.linkgem.presentation.user.dto.UserResponse;

public interface UserInfoService {

    UserResponse.UserInfoResponse info(Long userId);
}
