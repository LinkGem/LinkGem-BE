package com.linkgem.presentation.oauth;

import com.linkgem.domain.oauth.OauthServiceImpl;
import com.linkgem.presentation.oauth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OauthRestController {
  private final OauthServiceImpl oauthService;


  @GetMapping("/login/oauth/{provider}")
  public ResponseEntity<LoginResponse> login(@PathVariable String provider, @RequestParam String code) {
    LoginResponse loginResponse = oauthService.login(provider, code);
    return ResponseEntity.ok().body(loginResponse);
  }


}
