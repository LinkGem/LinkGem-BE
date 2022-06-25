package com.linkgem.domain.gembox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class GemBoxInfo {

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Create {
        private Long id;

        private String name;

        private Long userId;

        public static Create of(GemBox gemBox) {
            return Create.builder()
                .id(gemBox.getId())
                .name(gemBox.getName())
                .userId(gemBox.getUserId())
                .build();
        }

    }
}
