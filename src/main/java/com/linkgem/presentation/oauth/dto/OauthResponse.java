package com.linkgem.presentation.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.linkgem.domain.user.UserPhase;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OauthResponse {

  @Getter
  @ApiModel(description = "로그인 요청 응답")
  @NoArgsConstructor
  public static class LoginResponse {

    private Long id;
    private String longinEmail;
    private String mailEmail;
    private String name;
    private String nickname;
    private String accessToken;
    private String refreshToken;
    private String userPhase;


    @Builder
    public LoginResponse(Long id, String longinEmail, String nickname, String accessToken,
        String refreshToken, UserPhase userPhase, String mailEmail, String name) {
      this.id = id;
      this.nickname = nickname;
      this.name = name;
      this.longinEmail = longinEmail;
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
      this.userPhase = userPhase.name();
      this.mailEmail = mailEmail;
    }
  }

  @Getter
  @NoArgsConstructor
  public static class OauthTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    @Builder
    public OauthTokenResponse(String accessToken, String scope, String tokenType) {
      this.accessToken = accessToken;
      this.scope = scope;
      this.tokenType = tokenType;
    }
  }

  @ApiModel(description = "엑세스 토큰 재발급 요청 응답")
  @Getter
  @NoArgsConstructor
  public static class TokenReissueResponse {

    private String accessToken;

    @Builder
    public TokenReissueResponse(String accessToken) {
      this.accessToken = accessToken;
    }
  }

}
