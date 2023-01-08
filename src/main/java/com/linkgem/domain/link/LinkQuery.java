package com.linkgem.domain.link;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LinkQuery {

    @AllArgsConstructor
    @Getter
    public static class SearchLinks {
        private Long userId;
        private Long gemBoxId;
        private Boolean isFavorites;
        private Boolean hasMemo;
    }

}
