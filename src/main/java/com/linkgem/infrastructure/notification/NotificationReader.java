package com.linkgem.infrastructure.notification;

import java.util.List;
import java.util.Optional;

import com.linkgem.domain.notification.Notification;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationReader {
    Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable);

    Optional<Notification> findOne(NotificationQuery.FindOne findOneQuery);

    List<NotificationInfo.LatestNotification> findAllLatest(NotificationQuery.FindAllLatest findAllLatestQuery);
}
