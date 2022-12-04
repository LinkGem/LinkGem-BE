package com.linkgem.domain.notification;

public interface NotificationStore {
    Notification create(Notification notification);

    void delete(Notification notification);

}
