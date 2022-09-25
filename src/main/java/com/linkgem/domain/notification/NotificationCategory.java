package com.linkgem.domain.notification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationCategory {
    NOTICE("공지"), EVENT("이벤트"), CONTENTS("링크잼 콘텐츠");

    private final String description;
}
