package com.linkgem.infrastructure.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.linkgem.domain.notification.Notification;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;

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
    public Optional<Notification> findOne(NotificationQuery.FindOne findOneQuery) {
        return notificationRepository.findOne(findOneQuery);
    }

    @Override
    public List<NotificationInfo.LatestNotification> findAllLatest(NotificationQuery.FindAllLatest findAllLatestQuery) {
        return notificationRepository.findAllLatest(findAllLatestQuery);
    }
}
