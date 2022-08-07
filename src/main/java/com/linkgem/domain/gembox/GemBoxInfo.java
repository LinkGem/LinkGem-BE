package com.linkgem.domain.gembox;

import java.util.List;
import java.util.stream.Collectors;

import com.linkgem.domain.link.Link;

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

        public static Main of(GemBox gemBox) {
            return Main.builder()
                .id(gemBox.getId())
                .name(gemBox.getName())
                .build();
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Search {
        private Long id;

        private String name;

        private String imageUrl;
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
