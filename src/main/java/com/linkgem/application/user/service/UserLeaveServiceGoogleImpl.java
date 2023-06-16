package com.linkgem.application.user.service;

import com.linkgem.infrastructure.gembox.GemBoxStore;
import com.linkgem.infrastructure.link.LinkStore;
import com.linkgem.domain.user.User;
import com.linkgem.infrastructure.user.UserReader;
import com.linkgem.domain.user.provider.OauthProvider;
import com.linkgem.domain.user.provider.OauthProvider.Provider;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import com.linkgem.presentation.user.dto.UserRequest.UserLeaveRequest;
import com.linkgem.presentation.user.dto.UserResponse;
import com.linkgem.presentation.user.dto.UserResponse.OauthTokenResponse;
import com.linkgem.presentation.user.dto.UserResponse.UserLeaveResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
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
public class UserLeaveServiceGoogleImpl implements UserLeaveService{

    private final UserReader userReader;
    private final OauthProvider oauthProvider;
    private final GemBoxStore gemBoxStore;
    private final LinkStore linkStore;

    @Override
    @Transactional
    public UserLeaveResponse leave(UserLeaveRequest userLeaveRequest) {
        String code = userLeaveRequest.getCode();
        String providerName = userLeaveRequest.getProviderName();
        Long userId = userLeaveRequest.getUserId();
        User user = userReader.find(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.leave();
        gemBoxStore.deleteAllByUserId(userId);
        linkStore.deleteAllByUserId(userId);
        OauthProvider.Provider provider = oauthProvider.getProvider(providerName);
        UserResponse.OauthTokenResponse oauthTokenResponse = getToken(code, provider);
        String accessToken = oauthTokenResponse.getAccessToken();
        leaveOauth(accessToken,provider);
        return UserLeaveResponse.builder()
                .result("success")
                .build();
    }

    private void leaveOauth(String accessToken,
                            Provider provider) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("https://oauth2.googleapis.com/revoke")
                .queryParam("token",accessToken)
                .build(true);
        URI leaveUri = uriComponents.toUri();
        WebClient.create()
                .post()
                .uri(leaveUri)
                .headers(header -> {
                    header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .retrieve()
                .bodyToMono(void.class)
                .block();
    }

    @Override
    public Boolean getLeaveService(String providerName) {
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
}
