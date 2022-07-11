package com.linkgem.presentation.oauth;

import com.linkgem.domain.oauth.OauthService;
import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.oauth.dto.OauthResponse.LoginResponse;
import com.linkgem.presentation.oauth.dto.OauthResponse.TokenReissueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthRestApiController {

  private final OauthService oauthService;


  @GetMapping("/login/{provider}")
  public CommonResponse<LoginResponse> login(@PathVariable String provider,
      @RequestParam String code) {
    LoginResponse loginResponse = oauthService.login(provider, code);
    return CommonResponse.of(loginResponse);
  }

  @PostMapping("/reissue")
  public CommonResponse<TokenReissueResponse> reissue(
      @RequestHeader(value = "ACCESS-TOKEN") String accessToken,
      @RequestHeader(value = "REFRESH-TOKEN") String refreshToken) {
    TokenReissueResponse tokenReissueResponse = oauthService.reissue(accessToken, refreshToken);

    return CommonResponse.of(tokenReissueResponse);
  }


}
