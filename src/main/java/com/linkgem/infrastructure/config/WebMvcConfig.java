package com.linkgem.infrastructure.config;

import com.linkgem.infrastructure.config.Interceptor.JwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private final JwtTokenInterceptor jwtTokenInterceptor;
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(jwtTokenInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/","/oauth/**");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000")
        .allowedMethods("*")
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}
