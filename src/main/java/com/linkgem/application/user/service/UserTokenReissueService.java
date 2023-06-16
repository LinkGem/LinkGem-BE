package com.linkgem.application.user.service;

import com.linkgem.presentation.user.dto.UserResponse;

public interface UserTokenReissueService {

	UserResponse.TokenReissueResponse reissue(String accessToken,String refreshToken);
}
