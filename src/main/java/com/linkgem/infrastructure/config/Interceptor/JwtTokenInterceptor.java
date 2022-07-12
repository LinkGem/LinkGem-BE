package com.linkgem.infrastructure.config.Interceptor;

import com.linkgem.domain.oauth.TokenProvider;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
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
      String userId = tokenProvider.isValidAccessToken(access_token);
      request.setAttribute("userId",Long.getLong(userId));
      return true;
    } else {
      //TODO accessToken 없을시 응답코드
      throw new BusinessException(ErrorCode.ACCESS_TOKEN_IS_EMPTY);
    }

  }
}
