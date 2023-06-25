package com.linkgem.presentation.link.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.linkgem.domain.link.LinkInfo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class LinkResponse {
    private LinkResponse() {
    }

    @ApiModel(description = "링크 목록 조회 응답")
    @Getter
    public static class Main {

        private Long id;
        private String memo;
        private String url;

        private String title;
        private String description;
        private String imageUrl;
        private String siteName;
        private boolean isFavorites;

        private Long gemBoxId;

        private Long userId;
        private String userNickname;

        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        // isOpen(공유 api 응답) 인경우 에는 모든 정보를 보여주지 않도록한다.
        public Main(LinkInfo.Main linkInfo, boolean isOpen) {
            this.id = linkInfo.getId();
            this.url = linkInfo.getUrl();
            this.title = linkInfo.getTitle();
            this.description = linkInfo.getDescription();
            this.imageUrl = linkInfo.getImageUrl();
            this.siteName = linkInfo.getSiteName();
            this.gemBoxId = linkInfo.getGemBoxId();
            this.isFavorites = linkInfo.isFavorites();
            this.userId = isOpen ? null: linkInfo.getUserId();
            this.memo = isOpen ? null : linkInfo.getMemo();
            this.userNickname = isOpen ? null: linkInfo.getUserNickname();
            this.createDate = linkInfo.getCreateDate();
            this.updateDate = linkInfo.getUpdateDate();
        }

        public static Main of(LinkInfo.Main linkInfo) {
            return new Main(linkInfo, false);
        }
    }

    @ApiModel(description = "링크 상세 조회 응답")
    @Getter
    public static class SearchDetailLinkResponse extends Main {

        public SearchDetailLinkResponse(LinkInfo.Main linkInfo) {
            super(linkInfo, false);
        }

        public static SearchDetailLinkResponse of(LinkInfo.Main linkInfo) {
            return new SearchDetailLinkResponse(linkInfo);
        }
    }

    @ApiModel(description = "링크 목록 조회 응답")
    @Getter
    public static class SearchLinkResponse extends Main {

        public SearchLinkResponse(LinkInfo.Main linkInfo, boolean isOpen) {
            super(linkInfo, isOpen);
        }

        public static SearchLinkResponse of(LinkInfo.Main linkInfo) {
            return new SearchLinkResponse(linkInfo, false);
        }

        public static SearchLinkResponse of(LinkInfo.Main linkInfo, boolean isOpen) {
            return new SearchLinkResponse(linkInfo, isOpen);
        }

    }

    @ApiModel(description = "링크 생성 응답")
    @Getter
    public static class CreateLinkResponse extends Main {

        public CreateLinkResponse(LinkInfo.Main linkInfo) {
            super(linkInfo, false);
        }

        public static CreateLinkResponse of(LinkInfo.Create create) {
            return new CreateLinkResponse(create);
        }
    }

    @ApiModel(description = "링크 삭제 응답")
    @Builder
    @AllArgsConstructor
    @Getter
    public static class DeleteLinkResponse {
        private List<Long> deletedIds;
    }
}
