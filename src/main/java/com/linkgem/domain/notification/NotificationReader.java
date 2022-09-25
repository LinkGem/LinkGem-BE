package com.linkgem.domain.notification;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationReader {
    Page<NotificationInfo.Main> findAll(NotificationQuery.Search searchQuery, Pageable pageable);

    Long getUnReadNotificationCount(NotificationQuery.Search searchQuery);

    Optional<Notification> find(Long notificationId);
}
