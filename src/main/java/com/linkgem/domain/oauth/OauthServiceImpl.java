package com.linkgem.domain.oauth;

import com.linkgem.domain.user.User;
import com.linkgem.domain.user.UserProfile;
import com.linkgem.domain.user.UserRepository;
import com.linkgem.presentation.oauth.dto.LoginResponse;
import com.linkgem.presentation.oauth.dto.OauthTokenResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OauthServiceImpl implements OauthService {

  private final InMemoryProviderRepository inMemoryProviderRepository;

  private final UserRepository userRepository;

  private final TokenProvider tokenProvider;


  @Override
  public LoginResponse login(String providerName, String code) {
    // ����Ʈ���� �Ѿ�� provider �̸��� ���� InMemoryProviderRepository���� OauthProvider ��������
    OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

    // TODO access token ��������
    OauthTokenResponse tokenResponse = getToken(code, provider);
    // TODO ���� ���� ��������
    UserProfile userProfile = getUserProfile(providerName, tokenResponse, provider);
    System.out.println("userProfile = " + userProfile.getOauthId());
    System.out.println("userProfile.getEmail() = " + userProfile.getEmail());
    System.out.println("userProfile.getName() = " + userProfile.getName());

    User user = userRepository.findByEmail(userProfile.getEmail())
        .orElseGet(userProfile::toUser);

    String accessToken = tokenProvider.createAccessToken(user.getEmail());
    String refreshToken = tokenProvider.createRefreshToken(user.getEmail());
    user.updateRefreshToken(refreshToken);
    userRepository.save(user);
    return LoginResponse.builder()
        .id(user.getId())
        .nickname(user.getNickName())
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
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
    // TODO ���� ����(map)�� ���� UserProfile �����
    return OauthAttributes.extract(providerName, userAttributes);
  }

  // OAuth �������� ���� ���� map���� ��������
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
