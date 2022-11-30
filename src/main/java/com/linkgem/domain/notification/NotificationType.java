package com.linkgem.domain.notification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    NOTICE("공지사항"),
    EVENT("이벤트"),
    COMMUNITY("커뮤니티"),
    MESSAGE("개인 메시지"),
    ;

    private final String description;
}
