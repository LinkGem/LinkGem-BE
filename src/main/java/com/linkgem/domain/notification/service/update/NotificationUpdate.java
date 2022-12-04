package com.linkgem.domain.notification.service.update;

public interface NotificationUpdate {
    void updateToRead(Long notificationId);

    long updateAllToRead(Long userId);

}
