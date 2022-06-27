package com.linkgem.presentation;


import com.linkgem.presentation.Dto.LoginResponse;


public interface OauthService {

  LoginResponse login(String providerName, String code);

}
