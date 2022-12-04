package com.linkgem.domain.notification.service.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.domain.notification.NotificationReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationSearchImpl implements NotificationSearch {

    private final NotificationReader notificationReader;

    @Override
    public Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable) {
        return notificationReader.findAll(findAllQuery, pageable);
    }

    @Override
    public List<NotificationInfo.LatestNotification> findAllLatest(NotificationQuery.FindAllLatest findAllLatestQuery) {
        return notificationReader.findAllLatest(findAllLatestQuery);
    }
}
