package com.linkgem.domain.oauth;


import com.linkgem.presentation.oauth.dto.LoginResponse;


public interface OauthService {

  LoginResponse login(String providerName, String code);

}
