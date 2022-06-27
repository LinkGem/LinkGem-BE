package com.linkgem.domain.infrastructure.Interceptor;

import com.linkgem.domain.oauth.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

  private final TokenProvider tokenProvider;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    String access_token = request.getHeader("ACCESS_TOKEN");
    String refresh_token = request.getHeader("REFRESH_TOKEN");

    if (access_token != null) {
      try {
        return tokenProvider.isValidAccessToken(access_token);
      } catch (ExpiredJwtException e) {
        //TODO accessToken 만료시 응답코드
        return false;
      }
    } else {
        //TODO accessToken 없을시 응답코드
      return false;
    }

  }
}
