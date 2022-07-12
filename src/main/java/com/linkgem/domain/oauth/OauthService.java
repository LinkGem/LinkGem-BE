package com.linkgem.domain.oauth;


import com.linkgem.presentation.oauth.dto.OauthResponse.LoginResponse;
import com.linkgem.presentation.oauth.dto.OauthResponse.TokenReissueResponse;

public interface OauthService {

  LoginResponse login(String providerName, String code);

  TokenReissueResponse reissue(String accessToken,String refreshToken);

}
