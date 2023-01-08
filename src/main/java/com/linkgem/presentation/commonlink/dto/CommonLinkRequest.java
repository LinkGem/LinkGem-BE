package com.linkgem.presentation.commonlink.dto;

import javax.validation.constraints.Pattern;

import com.linkgem.domain.commonlink.CommonLinkCommand;
import com.linkgem.domain.commonlink.CommonLinkQuery;
import com.linkgem.domain.link.opengraph.OpenGraph;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommonLinkRequest {

    private CommonLinkRequest() {
    }

    @ApiModel(description = "링크 목록 조회 요청")
    @NoArgsConstructor
    @Setter
    @Getter
    public static class FindAll {
        public CommonLinkQuery.FindAll to() {
            return new CommonLinkQuery.FindAll();
        }
    }

    @ApiModel(description = "링크 생성 요청")
    @NoArgsConstructor
    @Getter
    public static class Create {

        @ApiModelProperty(value = "링크 URL", required = true)
        @Pattern(regexp = OpenGraph.URL_REGEX, message = "올바른 URL 형식이 아닙니다.")
        private String url;

        public CommonLinkCommand.Create to() {
            return CommonLinkCommand.Create.of(this.url);
        }

    }

    @ApiModel(description = "링크 삭제 요청")
    @NoArgsConstructor
    @Getter
    public static class DeleteOne {
        private Long id;

        public CommonLinkCommand.DeleteOne to() {
            return new CommonLinkCommand.DeleteOne(this.id);
        }
    }
}
