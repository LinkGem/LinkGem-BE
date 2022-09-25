package com.linkgem.domain.notification.service.update;

public interface NotificationUpdater {
    void updateToRead(Long notificationId);

    void updateToDeleted(Long notificationId);

}
