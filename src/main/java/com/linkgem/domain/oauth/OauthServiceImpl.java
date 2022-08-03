package com.linkgem.domain.oauth;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.linkgem.domain.user.User;
import com.linkgem.domain.user.UserPhase;
import com.linkgem.domain.user.UserProfile;
import com.linkgem.infrastructure.user.UserRepository;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import com.linkgem.presentation.oauth.dto.OauthResponse.LoginResponse;
import com.linkgem.presentation.oauth.dto.OauthResponse.OauthTokenResponse;
import com.linkgem.presentation.oauth.dto.OauthResponse.TokenReissueResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OauthServiceImpl implements OauthService {

	private final InMemoryProviderRepository inMemoryProviderRepository;

	private final UserRepository userRepository;

	private final TokenProvider tokenProvider;

	@Override
	@Transactional
	public LoginResponse login(String providerName, String code) {
		OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);
		OauthTokenResponse tokenResponse = getToken(code, provider);
		UserProfile userProfile = getUserProfile(providerName, tokenResponse, provider);
		User user = userRepository.findByLoginEmail(userProfile.getLoginEmail())
			.orElseGet(() -> userRepository.save(userProfile.toUser()));
		if (user.getUserPhase().equals(UserPhase.DELETED)) {
			user.resetReady();
		}
		String accessToken = tokenProvider.createAccessToken(user.getId().toString());
		String refreshToken = tokenProvider.createRefreshToken(user.getId().toString());
		return LoginResponse.builder()
			.id(user.getId())
			.name(user.getName())
			.nickname(user.getNickname())
			.loginEmail(user.getLoginEmail())
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.mailEmail(user.getMailEmail())
			.userPhase(user.getUserPhase())
			.build();
	}

	@Override
	public TokenReissueResponse reissue(String accessToken, String refreshToken) {
		if (tokenProvider.isExpiredAccessToken(accessToken)) {
			String userId = tokenProvider.isValidRefreshToken(refreshToken);
			String createdAccessToken = tokenProvider.createAccessToken(userId);
			return TokenReissueResponse.builder()
				.accessToken(createdAccessToken)
				.build();
		} else
			throw new BusinessException(ErrorCode.ACCESS_TOKEN_NOT_EXPIRED);
	}

	private OauthTokenResponse getToken(String code, OauthProvider provider) {
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
			.bodyToMono(OauthTokenResponse.class)
			.block();
	}

	private MultiValueMap<String, String> tokenRequest(String code, OauthProvider provider) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		formData.add("redirect_uri", provider.getRedirectUrl());
		return formData;
	}

	private UserProfile getUserProfile(String providerName, OauthTokenResponse tokenResponse,
		OauthProvider provider) {
		Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);
		for (String s : userAttributes.keySet()) {
			System.out.println("s : userAttributes.get(s).toString() = " + s +" : " + userAttributes.get(s).toString());
		}
		return OauthAttributes.extract(providerName, userAttributes);
	}

	private Map<String, Object> getUserAttributes(OauthProvider provider,
		OauthTokenResponse tokenResponse) {
		return WebClient.create()
			.get()
			.uri(provider.getUserInfoUrl())
			.headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();
	}

}
