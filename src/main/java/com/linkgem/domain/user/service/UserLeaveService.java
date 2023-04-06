package com.linkgem.domain.user.service;

import com.linkgem.domain.user.provider.OauthProvider;
import com.linkgem.domain.user.dto.UserRequest;
import com.linkgem.domain.user.dto.UserResponse;

public interface UserLeaveService {

	UserResponse.UserLeaveResponse leave(UserRequest.UserLeaveRequest userLeaveRequest);

	Boolean getLeaveService(String providerName);

	UserResponse.OauthTokenResponse getToken(String code, OauthProvider.Provider provider);
}
