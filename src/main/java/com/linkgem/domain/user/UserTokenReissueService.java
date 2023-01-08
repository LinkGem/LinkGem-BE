package com.linkgem.domain.user;

import com.linkgem.presentation.user.dto.UserResponse;

public interface UserTokenReissueService {

	UserResponse.TokenReissueResponse reissue(String accessToken,String refreshToken);
}
