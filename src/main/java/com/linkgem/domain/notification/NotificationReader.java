package com.linkgem.domain.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationReader {
    Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable);

    Optional<Notification> find(Long notificationId);

    List<NotificationInfo.LatestNotification> findAllLatest(NotificationQuery.FindAllLatest findAllLatestQuery);
}
