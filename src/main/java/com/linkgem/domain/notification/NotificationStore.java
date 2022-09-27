package com.linkgem.domain.notification;

public interface NotificationStore {
    Notification create(Notification notification);

    long updateAllToRead(Long userId);
}
