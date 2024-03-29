package com.linkgem.domain.notification.service.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;

public interface NotificationSearch {

    Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable);

    List<NotificationInfo.LatestNotification> findAllLatest(NotificationQuery.FindAllLatest findAllLatestQuery);
}
