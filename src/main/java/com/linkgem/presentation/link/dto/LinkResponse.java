package com.linkgem.presentation.link.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.linkgem.domain.link.LinkInfo;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class LinkResponse {

    @ApiModel(description = "링크 목록 조회 응답")
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Main {
        private Long id;
        private String memo;
        private String url;

        private String title;
        private String description;
        private String imageUrl;

        private Long userId;
        private String userNickname;

        private Boolean isFavorites;

        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        public static Main of(LinkInfo.Main linkInfo) {
            return Main.builder()
                .id(linkInfo.getId())
                .url(linkInfo.getUrl())
                .memo(linkInfo.getMemo())
                .title(linkInfo.getTitle())
                .description(linkInfo.getDescription())
                .imageUrl(linkInfo.getImageUrl())
                .userId(linkInfo.getUserId())
                .userNickname(linkInfo.getUserNickname())
                .isFavorites(linkInfo.isFavorites())
                .createDate(linkInfo.getCreateDate())
                .updateDate(linkInfo.getUpdateDate())
                .build();
        }
    }

    @ApiModel(description = "링크 상세 조회 응답")
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class SearchDetailLinkResponse {

        private Long id;
        private String memo;
        private String url;

        private String title;
        private String description;
        private String imageUrl;
        private Boolean isFavorites;

        private Long userId;
        private String userNickname;

        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private Long gemBoxId;

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private String gemBoxName;

        public static SearchDetailLinkResponse of(LinkInfo.Detail linkInfo) {
            return SearchDetailLinkResponse.builder()
                .id(linkInfo.getId())
                .url(linkInfo.getUrl())
                .memo(linkInfo.getMemo())
                .title(linkInfo.getTitle())
                .description(linkInfo.getDescription())
                .imageUrl(linkInfo.getImageUrl())
                .userId(linkInfo.getUserId())
                .userNickname(linkInfo.getUserNickname())
                .isFavorites(linkInfo.isFavorites())
                .createDate(linkInfo.getCreateDate())
                .updateDate(linkInfo.getUpdateDate())
                .gemBoxId(linkInfo.getGemBoxId())
                .gemBoxName(linkInfo.getGemBoxName())
                .build();
        }

    }

    @ApiModel(description = "링크 목록 조회 응답")
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class SearchLinkResponse {
        private Long id;
        private String memo;
        private String url;

        private String title;
        private String description;
        private String imageUrl;

        private Long userId;
        private String userNickname;
        private Boolean isFavorites;

        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        public static SearchLinkResponse of(LinkInfo.Search search) {
            return SearchLinkResponse.builder()
                .id(search.getId())
                .url(search.getUrl())
                .memo(search.getMemo())
                .title(search.getTitle())
                .description(search.getDescription())
                .imageUrl(search.getImageUrl())
                .userId(search.getUserId())
                .userNickname(search.getUserNickname())
                .isFavorites(search.isFavorites())
                .createDate(search.getCreateDate())
                .updateDate(search.getUpdateDate())
                .build();
        }
    }

    @ApiModel(description = "링크 생성 응답")
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class CreateLinkResponse {
        private Long id;
        private String memo;
        private String url;

        private String title;
        private String description;
        private String imageUrl;

        private Long userId;
        private String userNickname;

        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        public static CreateLinkResponse of(LinkInfo.Create create) {
            return CreateLinkResponse.builder()
                .id(create.getId())
                .url(create.getUrl())
                .memo(create.getMemo())
                .title(create.getTitle())
                .description(create.getDescription())
                .imageUrl(create.getImageUrl())
                .userId(create.getUserId())
                .userNickname(create.getUserNickname())
                .createDate(create.getCreateDate())
                .updateDate(create.getUpdateDate())
                .build();
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
