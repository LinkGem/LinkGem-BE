package com.linkgem.presentation.gembox.dto;

import com.linkgem.domain.gembox.GemBoxInfo;

public record GemBoxCreateResponse(Long id, String name) {
    public static GemBoxCreateResponse of(GemBoxInfo.Create create) {
        return new GemBoxCreateResponse(create.getId(), create.getName());
    }
}
