package com.linkgem.infrastructure.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.linkgem.domain.notification.Notification;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.domain.notification.NotificationReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NotificationReaderImpl implements NotificationReader {

    private final NotificationRepository notificationRepository;

    @Override
    public Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable) {
        return notificationRepository.findAll(findAllQuery, pageable);
    }

    @Override
    public Long getUnReadNotificationCount(NotificationQuery.FindAll findAllQuery) {
        return notificationRepository.getUnReadNotificationCount(findAllQuery);
    }

    @Override
    public Optional<Notification> find(Long notificationId) {
        return notificationRepository.findById(notificationId);
    }

    @Override
    public List<NotificationInfo.LatestNotification> findAllLatest(NotificationQuery.FindAllLatest findAllLatestQuery) {
        return notificationRepository.findAllLatest(findAllLatestQuery);
    }
}
