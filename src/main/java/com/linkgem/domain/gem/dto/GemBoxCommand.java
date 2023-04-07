package com.linkgem.domain.gem.dto;

import java.util.List;

import com.linkgem.domain.gem.domain.GemBox;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class GemBoxCommand {

    private GemBoxCommand() {
    }

    @Getter
    public static class Create {
        private String name;
        private List<Long> linkIds;
        private Long userId;
        private Boolean isDefault;

        public GemBox toEntity() {
            return GemBox.builder()
                .name(this.name)
                .userId(this.userId)
                .isDefault(this.isDefault)
                .build();
        }

        public static Create createDefault(Long userId) {
            return new Create(GemBox.DEFAULT_GEMBOX_NAME, null, userId, true);
        }

        @Builder
        private Create(String name, List<Long> linkIds, Long userId, boolean isDefault) {
            this.name = name;
            this.linkIds = linkIds;
            this.userId = userId;
            this.isDefault = isDefault;
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

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class PutLinksToGembox {
        private Long userId;
        private Long gemBoxId;
        private List<Long> linkIds;
    }

    @AllArgsConstructor
    @Getter
    public static class SearchDuplication {
        private Long id;
        private String name;
        private Long userId;

        public static GemBoxCommand.SearchDuplication of(Long id, String name, Long userId) {
            return new GemBoxCommand.SearchDuplication(id, name, userId);
        }

        public static GemBoxCommand.SearchDuplication of(String name, Long userId) {
            return of(null, name, userId);
        }

    }

    @AllArgsConstructor
    @Getter
    public static class SearchDetail {
        private Long id;
        private Long userId;

        public static GemBoxCommand.SearchDetail of(Long id, Long userId) {
            return new GemBoxCommand.SearchDetail(id, userId);
        }
    }

}
