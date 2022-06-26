package com.linkgem.domain.gembox;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class GemBoxCommand {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Create {
        private String name;
        private List<Long> linkIds;
        private Long userId;

        public GemBox toEntity() {
            return new GemBox(this.name, this.userId);
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Update {
        private Long id;
        private String name;
        private Long userId;
    }
}
