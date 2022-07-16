package com.linkgem.presentation.oauth;

import com.linkgem.domain.oauth.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@RequiredArgsConstructor
public class TestRunner implements ApplicationRunner {

  private final TokenProvider tokenProvider;

  @Override
  public void run(ApplicationArguments args) throws Exception {

  }
}
