package com.linkgem.domain.gem.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.linkgem.domain.gem.domain.GemBox;
import com.linkgem.domain.gem.domain.Link;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class GemBoxInfo {

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Main {
        private Long id;

        private String name;

        private Boolean isDefault;

        public static Main of(GemBox gemBox) {
            return Main.builder()
                .id(gemBox.getId())
                .name(gemBox.getName())
                .build();
        }
    }

    @Getter
    public static class Search extends Main {
        private String imageUrl;
        private Long linkCount;

        public Search(Long id, String name, Boolean isDefault, Long linkCount, String imageUrl) {
            super(id, name, isDefault);
            this.linkCount = linkCount;
            this.imageUrl = imageUrl;
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Create {
        private Long id;

        private String name;

        private Long userId;

        private List<Long> linkIds;

        public static Create of(GemBox gemBox) {

            return Create.builder()
                .id(gemBox.getId())
                .name(gemBox.getName())
                .userId(gemBox.getUserId())
                .linkIds(gemBox.getLinks().stream().map(Link::getId).collect(Collectors.toList()))
                .build();
        }

    }
}
