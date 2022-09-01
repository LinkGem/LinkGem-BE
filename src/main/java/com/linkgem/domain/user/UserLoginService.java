package com.linkgem.domain.user;

import com.linkgem.domain.user.provider.OauthProvider;
import com.linkgem.presentation.user.dto.UserResponse;

public interface UserLoginService {

	UserResponse.LoginResponse login(String providerName, String code);

	default OauthProvider.Provider getProvider(OauthProvider oauthProvider, String providerName) {
		return oauthProvider.getProvider().get(providerName);
	}
	Boolean getLoginService(String providerName);


}
