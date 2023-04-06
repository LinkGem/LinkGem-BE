package com.linkgem.domain.user.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import com.linkgem.domain.gembox.persistence.GemBoxPersistence;
import com.linkgem.domain.link.persistence.LinkPersistence;
import com.linkgem.domain.notification.service.NotificationService;
import com.linkgem.domain.user.persistence.UserPersistence;
import com.linkgem.domain.user.domain.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.linkgem.domain.notification.dto.NotificationCommand;
import com.linkgem.domain.user.provider.OauthProvider;
import com.linkgem.domain.common.exception.BusinessException;
import com.linkgem.domain.common.exception.ErrorCode;
import com.linkgem.domain.user.dto.UserRequest;
import com.linkgem.domain.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserLeaveServiceNaverImpl implements UserLeaveService {
	private final UserPersistence userPersistence;
	private final OauthProvider oauthProvider;
	private final GemBoxPersistence gemBoxPersistence;
	private final LinkPersistence linkPersistence;
	private final NotificationService notificationService;

	@Override
	@Transactional
	public UserResponse.UserLeaveResponse leave(UserRequest.UserLeaveRequest userLeaveRequest) {
		String code = userLeaveRequest.getCode();
		String providerName = userLeaveRequest.getProviderName();
		Long userId = userLeaveRequest.getUserId();
		User user = userPersistence.find(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		user.leave();
		gemBoxPersistence.deleteAllByUserId(userId);
		linkPersistence.deleteAllByUserId(userId);
		notificationService.deleteAll(NotificationCommand.DeleteAll.of(userId));
		OauthProvider.Provider provider = oauthProvider.getProvider(providerName);
		UserResponse.OauthTokenResponse oauthTokenResponse = getToken(code, provider);
		String accessToken = oauthTokenResponse.getAccessToken();
		return leaveOauth(accessToken, provider);
	}

	@Override
	public Boolean getLeaveService(String providerName) {
		return providerName.equals("naver");
	}

	@Override
	public UserResponse.OauthTokenResponse getToken(String code, OauthProvider.Provider provider) {
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