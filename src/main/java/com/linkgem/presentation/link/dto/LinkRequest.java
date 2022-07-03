package com.linkgem.presentation.link.dto;

import javax.validation.constraints.NotBlank;

import com.linkgem.domain.link.LinkCommand;

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
        @NotBlank(message = "URL은 필수 값 입니다")
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
