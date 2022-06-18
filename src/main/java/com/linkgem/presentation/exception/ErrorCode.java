package com.linkgem.presentation.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SAMPLE_CODE(HttpStatus.NOT_FOUND, "존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
