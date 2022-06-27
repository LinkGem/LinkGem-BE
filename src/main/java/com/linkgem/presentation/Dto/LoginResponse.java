package com.linkgem.presentation.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {

  private Long id;
  private String email;

  private String nickname;
  private String accessToken;
  private String refreshToken;

  @Builder
  public LoginResponse(Long id, String email, String nickname, String accessToken,
      String refreshToken) {
    this.id = id;
    this.nickname = nickname;
    this.email = email;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
