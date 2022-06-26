package com.linkgem.presentation.gembox.dto;

import com.linkgem.domain.gembox.GemBoxInfo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class GemBoxResponse {

    @ApiModel(description = "잼박스 목록 응답")
    @AllArgsConstructor
    @Getter
    public static class GemBox {
        private Long id;
        private String name;

        public static GemBox of(GemBoxInfo.Main gemboxInfo) {
            return new GemBox(gemboxInfo.getId(), gemboxInfo.getName());
        }
    }

    @ApiModel(description = "잼박스 생성 응답")
    @AllArgsConstructor
    @Getter
    public static class CreateResponse {
        private Long id;
        private String name;

        public static CreateResponse of(GemBoxInfo.Create create) {
            return new CreateResponse(create.getId(), create.getName());
        }
    }

}