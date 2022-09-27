package com.linkgem.infrastructure.notification;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.notification.Notification;
import com.linkgem.domain.notification.NotificationStore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NotificationStoreImpl implements NotificationStore {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification create(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public long updateAllToRead(Long userId) {
        return notificationRepository.updateAllToRead(userId);
    }
}
