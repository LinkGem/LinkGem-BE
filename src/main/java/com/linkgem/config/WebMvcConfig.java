package com.linkgem.config;

import com.linkgem.Interceptor.JwtTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtTokenInterceptor jwtTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/api/v1/user/oauth/**","/api/v1/auth/mail/check",
                "/v2/**", // swagger
                "/webjars/**", // swagger
                "/swagger**", // swagger
                "/swagger-resources/**");
    }
}
