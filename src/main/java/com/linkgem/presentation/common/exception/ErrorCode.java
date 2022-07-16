package com.linkgem.presentation.common.exception;

import org.springframework.http.HttpStatus;

import com.linkgem.domain.gembox.GemBox;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    GEMBOX_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 잼박스 입니다"),
    GEMBOX_ALREADY_EXISTED(HttpStatus.CONFLICT, "이미 존재하는 잼박스 입니다."),
    GEMBOX_IS_FULL(HttpStatus.BAD_REQUEST, String.format("%s%d%s", "잼박스는 최대", GemBox.MAX_GEMBOX, "개 까지 저장이 가능합니다.")),

    ACCESS_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "엑세스 토큰이 만료되었습니다."),
    ACCESS_TOKEN_NOT_EXPIRED(HttpStatus.BAD_REQUEST, "엑세스 토큰이 만료되지않았습니다."),
    ACCESS_TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "엑세스 토큰이 유효하지않습니다."),
    ACCESS_TOKEN_IS_EMPTY(HttpStatus.BAD_REQUEST, "엑세스 토큰이 없습니다."),
    REFRESH_TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "리프레시 토큰이 유효하지않습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다");

    private final HttpStatus httpStatus;
    private final String message;
}
