package com.linkgem.presentation.common.exception;

import org.springframework.http.HttpStatus;

import com.linkgem.domain.gembox.GemBox;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SAMPLE_CODE(HttpStatus.NOT_FOUND, "존재하지 않습니다."),

    GEMBOX_IS_ALREADY_EXISTED(HttpStatus.CONFLICT, "이미 존재하는 잼박스 입니다."),
    GEMBOX_IS_FULL(HttpStatus.BAD_REQUEST, String.format("%s%d%s", "잼박스는 최대", GemBox.MAX_GEMBOX, "개 까지 저장이 가능합니다."));

    private final HttpStatus httpStatus;
    private final String message;
}
