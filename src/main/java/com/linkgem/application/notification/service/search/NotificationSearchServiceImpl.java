package com.linkgem.application.notification.service.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.infrastructure.notification.NotificationReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationSearchServiceImpl implements NotificationSearchService {

    private final NotificationReader notificationReader;

    @Transactional(readOnly = true)
    @Override
    public Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable) {
        return notificationReader.findAll(findAllQuery, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationInfo.LatestNotification> findAllLatest(NotificationQuery.FindAllLatest findAllLatestQuery) {
        return notificationReader.findAllLatest(findAllLatestQuery);
    }
}
