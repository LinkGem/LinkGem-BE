package com.linkgem.domain.user.service;

import com.linkgem.domain.user.persistence.UserPersistence;
import com.linkgem.domain.user.domain.UserPhase;
import com.linkgem.domain.user.domain.OauthAttributes;
import com.linkgem.domain.user.domain.User;
import com.linkgem.domain.user.dto.UserInfo;
import com.linkgem.domain.user.provider.OauthProvider;
import com.linkgem.domain.user.provider.TokenProvider;
import com.linkgem.domain.common.file.aws.S3ObjectKeyCreator;
import com.linkgem.domain.user.dto.UserResponse;
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

@Service
@RequiredArgsConstructor
public class UserLoginServiceNaverImpl implements UserLoginService {

    private final OauthProvider oauthProvider;
    private final UserPersistence userPersistence;
    private final TokenProvider tokenProvider;

    private final S3ObjectKeyCreator s3ObjectKeyCreator;

    @Override
    @Transactional
    public UserResponse.LoginResponse login(String providerName, String code) {
        OauthProvider.Provider provider = getProvider(oauthProvider, "naver");
        UserResponse.OauthTokenResponse oauthTokenResponse = getToken(code, provider);
        UserInfo.UserProfile userProfile = getUserProfile(providerName, oauthTokenResponse, provider);
        User user = userPersistence.findByLoginEmail(userProfile.getLoginEmail())
                .orElseGet(() -> userPersistence.create(userProfile.toUser()));
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
    public OauthProvider.Provider getProvider(OauthProvider oauthProvider, String providerName) {
        return UserLoginService.super.getProvider(oauthProvider, providerName);
    }

    @Override
    public Boolean getLoginService(String providerName) {
        return providerName.equals("naver");
    }

    private MultiValueMap<String, String> tokenRequest(String code, OauthProvider.Provider provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUrl());
        return formData;
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

    @Override
    public UserInfo.UserProfile getUserProfile(String providerName, UserResponse.OauthTokenResponse oauthTokenResponse,
                                               OauthProvider.Provider provider) {
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
