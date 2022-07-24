package com.linkgem.infrastructure.config.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.linkgem.domain.oauth.TokenProvider;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

  private final TokenProvider tokenProvider;

  public static String USER_INFORMATION_NAME = "userId";

  public static String USER_TOKEN_HEADER = "authorization";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

    String authentication = request.getHeader(USER_TOKEN_HEADER);

    if (authentication != null) {
      String userId = tokenProvider.isValidAccessToken(authentication);
      request.setAttribute(USER_INFORMATION_NAME,Long.valueOf(userId));
      return true;
    } else {
      throw new BusinessException(ErrorCode.ACCESS_TOKEN_IS_EMPTY);
    }

  }
}
