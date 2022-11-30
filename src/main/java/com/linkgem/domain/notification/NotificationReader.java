package com.linkgem.domain.notification;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationReader {
    Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable);

    Long getUnReadNotificationCount(NotificationQuery.FindAll findAllQuery);

    Optional<Notification> find(Long notificationId);
}
