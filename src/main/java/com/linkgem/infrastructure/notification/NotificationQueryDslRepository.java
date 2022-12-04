package com.linkgem.infrastructure.notification;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;

public interface NotificationQueryDslRepository {

    Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable);

    Long getUnReadNotificationCount(NotificationQuery.FindAll findAllQuery);

    long updateAllToRead(Long userId);

    List<NotificationInfo.LatestNotification> findAllLatest(
        NotificationQuery.FindAllLatest findAllLatestQuery);

}
