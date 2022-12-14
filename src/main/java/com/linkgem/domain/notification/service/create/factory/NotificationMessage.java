package com.linkgem.domain.notification.service.create.factory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotificationMessage {

    JOIN("<mark>%s</mark> 키퍼님 반가워요✨\n링크잼에서 보석 같은 나만의 링크들을 쉽게 관리해 보세요.");
    private final String message;
}