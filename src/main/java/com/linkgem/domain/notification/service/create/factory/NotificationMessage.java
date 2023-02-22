package com.linkgem.domain.notification.service.create.factory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotificationMessage {

    JOIN("<mark>%s</mark> 키퍼님 반가워요✨<br/>링크잼에서 보석 같은 나만의 링크들을 쉽게 관리해 보세요."),

    EVENT20230227("링크잼이 더 나은 서비스를 제공할 수 있도록 피드백을 남겨주세요! 마음가득 선물을 드립니다.\uD83E\uDDE1");
    private final String message;
}
