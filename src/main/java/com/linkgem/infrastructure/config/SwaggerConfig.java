
package com.linkgem.infrastructure.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import com.fasterxml.classmate.TypeResolver;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RequiredArgsConstructor
@Configuration
@EnableSwagger2
public class SwaggerConfig {

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
            .paths(PathSelectors.regex("/api.*"))
            .build()
            .consumes(getConsumes())
            .produces(Stream.of(MediaType.APPLICATION_JSON_VALUE).collect(Collectors.toSet()))
            .useDefaultResponseMessages(false)
            .directModelSubstitute(LocalDateTime.class, String.class)
            .directModelSubstitute(LocalDate.class, String.class)
            .directModelSubstitute(LocalTime.class, String.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Link Gem API")
            .description("Link Gem swagger")
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
