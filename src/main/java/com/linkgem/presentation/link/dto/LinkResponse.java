package com.linkgem.presentation.link.dto;

import java.time.LocalDateTime;

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
    public static class SearchLinkResponse {
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

}
