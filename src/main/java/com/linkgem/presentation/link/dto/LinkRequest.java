package com.linkgem.presentation.link.dto;

import javax.validation.constraints.Pattern;

import com.linkgem.domain.link.LinkCommand;
import com.linkgem.domain.link.opengraph.OpenGraph;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

public class LinkRequest {

    @ApiModel(description = "링크 생성 요청")
    @Setter
    @AllArgsConstructor
    public static class CreateRequest {

        @ApiModelProperty(value = "링크 URL", required = true)
        @Pattern(regexp = OpenGraph.URL_REGEX, message = "올바른 URL 형식이 아닙니다.")
        private String url;

        @ApiModelProperty(value = "링크 메모", required = false)
        private String memo;

        public LinkCommand.Create to(Long userId) {
            return LinkCommand.Create.builder()
                .url(this.url)
                .memo(this.memo)
                .userId(userId)
                .build();
        }
    }
}
