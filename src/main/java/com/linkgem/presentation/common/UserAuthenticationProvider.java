package com.linkgem.presentation.common;

import javax.servlet.http.HttpServletRequest;

import com.linkgem.infrastructure.config.Interceptor.JwtTokenInterceptor;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAuthenticationProvider {

    public static Long provider(HttpServletRequest request) {
        Object userId = request.getAttribute(JwtTokenInterceptor.USER_INFORMATION_NAME);

        if (userId == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        return (long)userId;
    }
}
