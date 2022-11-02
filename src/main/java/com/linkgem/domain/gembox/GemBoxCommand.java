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
        private Boolean isDefault;

        public GemBox toEntity() {
            return new GemBox(this.name, this.userId, this.isDefault);
        }

        public static Create createDefault(Long userId) {
            return new Create(GemBox.DEFAULT_GEMBOX_NAME, null, userId, true);
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

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Delete {
        private Long id;
        private Long userId;

        public static Delete of(Long id, Long userId) {
            return new Delete(id, userId);
        }
    }
}
