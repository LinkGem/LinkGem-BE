package com.linkgem.domain.gembox;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class GemBoxQuery {

    @AllArgsConstructor
    @Getter
    public static class SearchDuplication {
        private Long id;
        private String name;
        private Long userId;

        public static SearchDuplication of(Long id, String name, Long userId) {
            return new SearchDuplication(id, name, userId);
        }

        public static SearchDuplication of(String name, Long userId) {
            return of(null, name, userId);
        }

    }
}
