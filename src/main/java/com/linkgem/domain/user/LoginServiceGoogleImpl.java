package com.linkgem.domain.user;

import com.linkgem.domain.user.UserInfo.UserProfile;
import com.linkgem.domain.user.provider.OauthProvider;
import com.linkgem.domain.user.provider.OauthProvider.Provider;
import com.linkgem.domain.user.provider.TokenProvider;
import com.linkgem.infrastructure.common.aws.S3ObjectKeyCreator;
import com.linkgem.presentation.user.dto.UserResponse;
import com.linkgem.presentation.user.dto.UserResponse.LoginResponse;
import com.linkgem.presentation.user.dto.UserResponse.OauthTokenResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class LoginServiceGoogleImpl implements UserLoginService {

    private final OauthProvider oauthProvider;
    private final UserReader userReader;
    private final UserStore userStore;
    private final TokenProvider tokenProvider;
    private final S3ObjectKeyCreator s3ObjectKeyCreator;

    @Override
    @Transactional
    public LoginResponse login(String providerName, String code) {
        Provider provider = getProvider(oauthProvider, "google");
        OauthTokenResponse oauthTokenResponse = getToken(code, provider);
        UserInfo.UserProfile userProfile = getUserProfile(providerName, oauthTokenResponse, provider);
        User user = userReader.findByLoginEmail(userProfile.getLoginEmail())
                .orElseGet(() -> userStore.create(userProfile.toUser()));
        if (user.getUserPhase().equals(UserPhase.DELETED)) {
            user.updateUserPhaseReady();
        }
        if (Objects.isNull(user.getProfileImageUrl())) {
            String defaultImageUrl = s3ObjectKeyCreator.createDefaultImageUrl();
            user.updateProfileImageUrl(defaultImageUrl);
        }
        String accessToken = tokenProvider.createAccessToken(user.getId().toString());
        String refreshToken = tokenProvider.createRefreshToken(user.getId().toString());
        return UserResponse.LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .loginEmail(user.getLoginEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .mailEmail(user.getMailEmail())
                .userPhase(user.getUserPhase())
                .careerYear(user.getCareerYear())
                .jobName(user.getJob())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }

    @Override
    public Provider getProvider(OauthProvider oauthProvider, String providerName) {
        return UserLoginService.super.getProvider(oauthProvider, providerName);
    }

    @Override
    public Boolean getLoginService(String providerName) {
        return providerName.equals("google");
    }

    @Override
    public OauthTokenResponse getToken(String code, Provider provider) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(provider.getTokenUrl())
                .queryParams(tokenRequest(code, provider))
                .build(true);
        URI tokenUri = uriComponents.toUri();
        return WebClient.create()
                .post()
                .uri(tokenUri)
                .headers(header -> {
                    header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .retrieve()
                .bodyToMono(OauthTokenResponse.class).block();
    }

    private MultiValueMap<String, String> tokenRequest(String code, OauthProvider.Provider provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", provider.getClientId());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUrl());
        return formData;
    }

    @Override
    public UserProfile getUserProfile(String providerName, OauthTokenResponse oauthTokenResponse, Provider provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, oauthTokenResponse);
        return OauthAttributes.extract(providerName, userAttributes);
    }

    private Map<String, Object> getUserAttributes(OauthProvider.Provider provider,
                                                  UserResponse.OauthTokenResponse oauthTokenResponse) {
        return WebClient.create()
                .get()
                .uri(provider.getUserInfoUrl())
                .headers(header -> header.setBearerAuth(oauthTokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }
}
