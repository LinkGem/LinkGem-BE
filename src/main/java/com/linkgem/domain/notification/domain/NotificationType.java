package com.linkgem.domain.notification.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotificationType {
    EVENT("이벤트", 1),
    COMMUNITY("커뮤니티", 2),
    NOTICE("공지사항", 3),
    MESSAGE("내 알림", 4),
    ;

    private final String description;
    private final int order;
}
