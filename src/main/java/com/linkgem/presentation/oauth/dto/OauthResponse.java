package com.linkgem.presentation.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OauthResponse {

  @Getter
  @NoArgsConstructor
  public static class LoginResponse {

    private Long id;
    private String email;

    private String nickName;
    private String accessToken;
    private String refreshToken;

    @Builder
    public LoginResponse(Long id, String email, String nickName, String accessToken,
        String refreshToken) {
      this.id = id;
      this.nickName = nickName;
      this.email = email;
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
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

  @Getter
  @NoArgsConstructor
  public static class TokenReissueResponse {
    private String accessToken;

    @Builder
    public TokenReissueResponse(String accessToken){
      this.accessToken = accessToken;
    }
  }

}
