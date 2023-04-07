package com.linkgem.domain.gem.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class LinkCommand {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Create {

        private String url;
        private String memo;
        private Long userId;

    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Delete {
        private List<Long> ids;
        private Long userId;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Update {
        private Long id;
        private Long userId;

        private Boolean isFavorites;

        private String memo;
        private Long gemBoxId;
    }

    @AllArgsConstructor
    @Getter
    public static class SearchLinks {
        private Long userId;
        private Long gemBoxId;
        private Boolean isFavorites;
        private Boolean hasMemo;
        private Boolean isDefault;
    }
}
