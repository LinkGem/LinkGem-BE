package com.linkgem.domain.notification.service.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;

public interface NotificationSearchService {

    Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable);

    Long getUnReadNotificationCount(NotificationQuery.FindAll findAllQuery);
}
