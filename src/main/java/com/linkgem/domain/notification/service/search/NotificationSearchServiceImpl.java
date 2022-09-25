package com.linkgem.domain.notification.service.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.domain.notification.NotificationReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationSearchServiceImpl implements NotificationSearchService {


    private final NotificationReader notificationReader;

    @Override
    public Page<NotificationInfo.Main> findAll(NotificationQuery.Search searchQuery, Pageable pageable) {
        return notificationReader.findAll(searchQuery, pageable);
    }

    @Override
    public Long getUnReadNotificationCount(NotificationQuery.Search searchQuery) {
        return notificationReader.getUnReadNotificationCount(searchQuery);
    }
}
