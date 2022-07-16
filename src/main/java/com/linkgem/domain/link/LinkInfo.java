package com.linkgem.domain.link;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class LinkInfo {

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

        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        public static Main of(Link link) {
            return Main.builder()
                .id(link.getId())
                .memo(link.getMemo())
                .url(link.getUrl())
                .title(link.getOpenGraph().getTitle())
                .description(link.getOpenGraph().getDescription())
                .imageUrl(link.getOpenGraph().getImageUrl())
                .userId(link.getUser().getId())
                .userNickname(link.getUser().getNickname())
                .createDate(link.getCreateDate())
                .updateDate(link.getUpdateDate())
                .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Search {

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

        public static Search of(Link link) {
            return Search.builder()
                .id(link.getId())
                .memo(link.getMemo())
                .url(link.getUrl())
                .title(link.getOpenGraph().getTitle())
                .description(link.getOpenGraph().getDescription())
                .imageUrl(link.getOpenGraph().getImageUrl())
                .userId(link.getUser().getId())
                .userNickname(link.getUser().getNickname())
                .createDate(link.getCreateDate())
                .updateDate(link.getUpdateDate())
                .build();
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Create {

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

        public static Create of(Link link) {
            return Create.builder()
                .id(link.getId())
                .memo(link.getMemo())
                .url(link.getUrl())
                .title(link.getOpenGraph().getTitle())
                .description(link.getOpenGraph().getDescription())
                .imageUrl(link.getOpenGraph().getImageUrl())
                .userId(link.getUser().getId())
                .userNickname(link.getUser().getNickname())
                .createDate(link.getCreateDate())
                .updateDate(link.getUpdateDate())
                .build();
        }
    }
}
