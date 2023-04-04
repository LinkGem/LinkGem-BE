package com.linkgem.presentation.link.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.linkgem.domain.link.LinkCommand;
import com.linkgem.domain.link.opengraph.OpenGraph;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LinkRequest {

    private LinkRequest() {
    }

    @ApiModel(description = "링크 목록 조회 요청")
    @NoArgsConstructor
    @Setter
    @Getter
    public static class SearchLinksRequest {

        @ApiModelProperty(value = "디폴스 잼박스에 포함 여부")
        private Boolean isDefault;

        @ApiModelProperty(value = "잼박스 고유 아이디")
        private Long gemBoxId;

        @ApiModelProperty(value = "즐겨찾기 조회", allowableValues = "true, false")
        private Boolean isFavorites;

        @ApiModelProperty(value = "메모 저장 여부", allowableValues = "true, false")
        private Boolean hasMemo;

        public LinkCommand.SearchLinks to(Long userId) {
            return new LinkCommand.SearchLinks(userId, this.gemBoxId, this.isFavorites, this.hasMemo, this.isDefault);
        }
    }

    @ApiModel(description = "링크 생성 요청")
    @NoArgsConstructor
    @Getter
    public static class CreateLinkRequest {

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

    @ApiModel(description = "링크 삭제 요청")
    @NoArgsConstructor
    @Getter
    public static class DeleteLinkRequest {

        @ApiModelProperty(value = "링크 id 리스트", required = true)
        @NotEmpty(message = "link id는 필수 값입니다.")
        private List<Long> ids;

        public LinkCommand.Delete to(Long userId) {
            return LinkCommand.Delete.builder()
                .userId(userId)
                .ids(this.ids)
                .build();
        }
    }

    @ApiModel(description = "링크 수정 요청")
    @NoArgsConstructor
    @Getter
    public static class UpdateLinkRequest {

        private String memo;

        private Long gemBoxId;

        private Boolean isFavorites;

        public LinkCommand.Update to(Long id, Long userId) {
            return LinkCommand.Update.builder()
                .userId(userId)
                .id(id)
                .memo(this.memo)
                .isFavorites(this.isFavorites)
                .gemBoxId(this.gemBoxId)
                .build();
        }
    }
}
