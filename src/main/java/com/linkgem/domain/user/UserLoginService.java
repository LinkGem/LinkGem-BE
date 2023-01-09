package com.linkgem.domain.user;

import com.linkgem.domain.user.UserInfo.UserProfile;
import com.linkgem.domain.user.provider.OauthProvider;
import com.linkgem.domain.user.provider.OauthProvider.Provider;
import com.linkgem.presentation.user.dto.UserResponse;
import com.linkgem.presentation.user.dto.UserResponse.OauthTokenResponse;
import java.net.URISyntaxException;

public interface UserLoginService {

	UserResponse.LoginResponse login(String providerName, String code);

	default OauthProvider.Provider getProvider(OauthProvider oauthProvider, String providerName) {
		return oauthProvider.getProvider().get(providerName);
	}
	Boolean getLoginService(String providerName);
	OauthTokenResponse getToken(String code, OauthProvider.Provider provider) throws URISyntaxException;

	UserProfile getUserProfile(String providerName, OauthTokenResponse oauthTokenResponse, Provider provider);
}
