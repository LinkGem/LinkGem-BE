package com.linkgem.domain.link;

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
}
