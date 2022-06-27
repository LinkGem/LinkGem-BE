package com.linkgem.domain.infrastructure.config;

import com.linkgem.domain.oauth.InMemoryProviderRepository;
import com.linkgem.domain.oauth.OauthAdapter;
import com.linkgem.domain.oauth.OauthProperties;
import com.linkgem.domain.oauth.OauthProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OauthProperties.class)
public class OauthConfig {

  private final OauthProperties properties;

  @Bean
  public InMemoryProviderRepository inMemoryProviderRepository() {
    Map<String, OauthProvider> providers = OauthAdapter.getOauthProviders(properties);
    return new InMemoryProviderRepository(providers);
  }


}
