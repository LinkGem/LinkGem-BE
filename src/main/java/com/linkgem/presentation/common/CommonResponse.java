package com.linkgem.presentation.common;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CommonResponse<T> {
    private final T result;
    private final LocalDateTime timestamp = LocalDateTime.now();

    private CommonResponse(T result) {
        this.result = result;
    }

    public static <T> CommonResponse<T> of(final T result) {
        return new CommonResponse<>(result);
    }
}
