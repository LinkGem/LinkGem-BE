
package com.linkgem.infrastructure.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;
import com.linkgem.infrastructure.config.Interceptor.JwtTokenInterceptor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile("dev")
@RequiredArgsConstructor
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String DEFAULT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaXNzIjoiTElOS19HRU0iLCJpYXQiOjE2NTc3MTQ3NzV9.PLAL9te0_Tszon7MMMPzMmDj7Cumt4nJGSVbx_6UT0g";
    private final TypeResolver typeResolver;

    private final String version = "0.0.1";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .alternateTypeRules(
                AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(Page.class)))
            .apiInfo(apiInfo())
            .groupName("API V1")
            .select()
            .paths(PathSelectors.regex("/api/v1.*"))
            .build()
            .consumes(getConsumes())
            .produces(Stream.of(MediaType.APPLICATION_JSON_VALUE).collect(Collectors.toSet()))
            .securitySchemes(Lists.newArrayList(apiKey()))
            .securityContexts(Lists.newArrayList(securityContext()))
            .useDefaultResponseMessages(false)
            .directModelSubstitute(LocalDateTime.class, String.class)
            .directModelSubstitute(LocalDate.class, String.class)
            .directModelSubstitute(LocalTime.class, String.class);
    }

    private ApiInfo apiInfo() {
        String description = new StringBuilder()
            .append("Link Gem swagger<br/>")
            .append(String.format("API KEY header: %s<br/>", JwtTokenInterceptor.USER_TOKEN_HEADER))
            .append(String.format("default token: %s<br/>", DEFAULT_TOKEN))
            .toString();

        return new ApiInfoBuilder()
            .title("Link Gem API")
            .description(description)
            .version(version)
            .build();
    }

    private Set<String> getConsumes() {
        return Stream.of(MediaType.ALL_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                MediaType.MULTIPART_FORM_DATA_VALUE)
            .collect(Collectors.toSet());
    }

    private ApiKey apiKey() {
        return new ApiKey(JwtTokenInterceptor.USER_TOKEN_HEADER, JwtTokenInterceptor.USER_TOKEN_HEADER, "header");
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference(JwtTokenInterceptor.USER_TOKEN_HEADER, authorizationScopes));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("/api/v1.*"))
            .build();
    }

    @Getter
    @Setter
    @ApiModel
    static class Page {
        @ApiModelProperty(value = "페이지 번호(0..N)")
        private Integer page;

        @ApiModelProperty(value = "페이지 크기", allowableValues = "range[0, 500]")
        private Integer size;
    }

}
