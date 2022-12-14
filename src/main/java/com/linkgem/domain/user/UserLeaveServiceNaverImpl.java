package com.linkgem.domain.user;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.linkgem.domain.gembox.GemBoxStore;
import com.linkgem.domain.link.LinkStore;
import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationStore;
import com.linkgem.domain.user.provider.OauthProvider;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import com.linkgem.presentation.user.dto.UserRequest;
import com.linkgem.presentation.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserLeaveServiceNaverImpl implements UserLeaveService {
	private final UserReader userReader;
	private final OauthProvider oauthProvider;
	private final GemBoxStore gemBoxStore;
	private final LinkStore linkStore;
	private final NotificationStore notificationStore;

	@Override
	@Transactional
	public UserResponse.UserLeaveResponse leave(UserRequest.UserLeaveRequest userLeaveRequest) {
		String code = userLeaveRequest.getCode();
		String providerName = userLeaveRequest.getProviderName();
		Long userId = userLeaveRequest.getUserId();
		User user = userReader.find(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		user.leave();
		gemBoxStore.deleteAllByUserId(userId);
		linkStore.deleteAllByUserId(userId);
		notificationStore.deleteAll(NotificationCommand.DeleteAll.of(userId));
		OauthProvider.Provider provider = oauthProvider.getProvider(providerName);
		UserResponse.OauthTokenResponse oauthTokenResponse = getToken(code, provider);
		String accessToken = oauthTokenResponse.getAccessToken();
		return leaveOauth(accessToken, provider);
	}

	@Override
	public Boolean getLeaveService(String providerName) {
		return providerName.equals("naver");
	}

	private UserResponse.OauthTokenResponse getToken(String code, OauthProvider.Provider provider) {
		return WebClient.create()
			.post()
			.uri(provider.getTokenUrl())
			.headers(header -> {
				header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})
			.bodyValue(tokenRequest(code, provider))
			.retrieve()
			.bodyToMono(UserResponse.OauthTokenResponse.class)
			.block();
	}

	private MultiValueMap<String, String> tokenRequest(String code, OauthProvider.Provider provider) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		formData.add("redirect_uri", provider.getRedirectUrl());
		return formData;
	}

	private MultiValueMap<String, String> leaveRequest(String accessToken, OauthProvider.Provider provider) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("service_provider", "naver");
		formData.add("grant_type", "delete");
		formData.add("access_token", accessToken);
		formData.add("client_id", provider.getClientId());
		formData.add("client_secret", provider.getClientSecret());
		return formData;
	}

	private UserResponse.UserLeaveResponse leaveOauth(String accessToken,
		OauthProvider.Provider provider) {
		return WebClient.create()
			.post()
			.uri(provider.getTokenUrl())
			.headers(header -> {
				header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})
			.bodyValue(leaveRequest(accessToken, provider))
			.retrieve()
			.bodyToMono(UserResponse.UserLeaveResponse.class)
			.block();
	}
}
