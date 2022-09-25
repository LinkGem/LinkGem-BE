package com.linkgem.infrastructure.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;

public interface NotificationRepositoryCustom {

    Page<NotificationInfo.Main> findAll(NotificationQuery.Search searchQuery, Pageable pageable);

    Long getUnReadNotificationCount(NotificationQuery.Search searchQuery);

}
