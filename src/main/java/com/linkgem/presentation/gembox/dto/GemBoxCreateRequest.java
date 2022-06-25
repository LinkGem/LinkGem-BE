package com.linkgem.presentation.gembox.dto;

import java.util.List;

import javax.validation.constraints.Pattern;

import com.linkgem.domain.gembox.GemBox;
import com.linkgem.domain.gembox.GemBoxCommand;

public record GemBoxCreateRequest(
    @Pattern(regexp = GemBox.GEMBOX_NAME_REGEX, message = "잼박스 이름은 최대 8글자 숫자/한글/영문/공백 문자만 입력이 가능합니다.") String name,
    List<Long> linkIds
) {

    public GemBoxCommand.Create to(Long userId) {
        return GemBoxCommand.Create.builder()
            .name(this.name)
            .linkIds(this.linkIds)
            .userId(userId)
            .build();
    }
}
