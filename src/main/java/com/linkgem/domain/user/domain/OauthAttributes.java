package com.linkgem.domain.user.domain;

import java.util.Arrays;
import java.util.Map;

import com.linkgem.domain.user.dto.UserInfo;
import com.linkgem.domain.common.exception.BusinessException;
import com.linkgem.domain.common.exception.ErrorCode;

public enum OauthAttributes {
  NAVER("naver") {
    @Override
    public UserInfo.UserProfile of(Map<String, Object> attributes) {
      Map<String, Object> response = (Map<String, Object>) attributes.get("response");
      return UserInfo.UserProfile.builder()
          .oauthId(NAVER.providerName + "_" + response.get("id").toString())
          .loginEmail((String) response.get("email"))
          .name((String) response.get("name"))
          .build();
    }
  },
  GOOGLE("google") {
    @Override
    public UserInfo.UserProfile of(Map<String, Object> attributes) {
      return UserInfo.UserProfile.builder()
          .oauthId(GOOGLE.providerName + "_" + attributes.get("sub").toString())
          .loginEmail((String) attributes.get("email"))
          .name((String) attributes.get("name"))
          .build();
    }
  };

  private final String providerName;

  OauthAttributes(String name) {
    this.providerName = name;
  }

  public static UserInfo.UserProfile extract(String providerName, Map<String, Object> attributes) {
    return Arrays.stream(values())
        .filter(provider -> providerName.equals(provider.providerName))
        .findFirst()
        .orElseThrow(()->new BusinessException(ErrorCode.PROVIDER_NOT_VALID))
        .of(attributes);
  }

  public abstract UserInfo.UserProfile of(Map<String, Object> attributes);
}
