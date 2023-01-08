package com.linkgem.presentation.gembox.dto;

import java.util.List;

import com.linkgem.domain.gembox.GemBoxInfo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class GemBoxResponse {

    @ApiModel(description = "잼박스 목록 응답")
    @AllArgsConstructor
    @Getter
    public static class Main {
        private Long id;
        private String name;
        private Boolean isDefault;

        public static Main of(GemBoxInfo.Main gemboxInfo) {
            return new Main(gemboxInfo.getId(), gemboxInfo.getName(), gemboxInfo.getIsDefault());
        }
    }

    @ApiModel(description = "잼박스 목록 응답")
    @Getter
    public static class Search extends Main {

        private String imageUrl;
        private Long linkCount;

        public Search(Long id, String name, Boolean isDefault, Long linkCount, String imageUrl) {
            super(id, name, isDefault);
            this.imageUrl = imageUrl;
            this.linkCount = linkCount;
        }

        public static Search of(GemBoxInfo.Search gemboxInfo) {
            return new Search(
                gemboxInfo.getId(),
                gemboxInfo.getName(),
                gemboxInfo.getIsDefault(),
                gemboxInfo.getLinkCount(),
                gemboxInfo.getImageUrl());
        }
    }

    @ApiModel(description = "잼박스 생성 응답")
    @AllArgsConstructor
    @Getter
    public static class CreateGemboxResponse {
        private Long id;
        private String name;

        private List<Long> linkIds;

        public static CreateGemboxResponse of(GemBoxInfo.Create create) {
            return new CreateGemboxResponse(create.getId(), create.getName(), create.getLinkIds());
        }
    }

}
