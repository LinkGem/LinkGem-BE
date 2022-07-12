package com.linkgem.domain.oauth;

import com.linkgem.domain.user.UserProfile;
import java.util.Arrays;
import java.util.Map;

public enum OauthAttributes {
  NAVER("naver") {
    @Override
    public UserProfile of(Map<String, Object> attributes) {
      Map<String, Object> response = (Map<String, Object>) attributes.get("response");
      return UserProfile.builder()
          .oauthId(NAVER.providerName + "_" + response.get("id").toString())
          .email((String) response.get("email"))
          .name((String) response.get("name"))
          .build();
    }
  },
  GOOGLE("google") {
    @Override
    public UserProfile of(Map<String, Object> attributes) {
      return UserProfile.builder()
          .oauthId(GOOGLE.providerName + "_" + attributes.get("sub").toString())
          .email((String) attributes.get("email"))
          .name((String) attributes.get("name"))
          .build();
    }
  };

  private final String providerName;

  OauthAttributes(String name) {
    this.providerName = name;
  }

  public static UserProfile extract(String providerName, Map<String, Object> attributes) {
    return Arrays.stream(values())
        .filter(provider -> providerName.equals(provider.providerName))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new)
        .of(attributes);
  }

  public abstract UserProfile of(Map<String, Object> attributes);
}
