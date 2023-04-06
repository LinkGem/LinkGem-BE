package com.linkgem.domain.notification.persistence;

import java.util.List;
import java.util.Optional;

import com.linkgem.domain.notification.dto.NotificationCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.linkgem.domain.notification.domain.Notification;
import com.linkgem.domain.notification.dto.NotificationInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NotificationPersistenceImpl implements NotificationPersistence {

    private final NotificationRepository notificationRepository;

    @Override
    public Page<NotificationInfo.Main> findAll(NotificationCommand.FindAll findAllQuery, Pageable pageable) {
        return notificationRepository.findAll(findAllQuery, pageable);
    }

    @Override
    public Optional<Notification> findOne(NotificationCommand.FindOne findOneQuery) {
        return notificationRepository.findOne(findOneQuery);
    }

    @Override
    public List<NotificationInfo.LatestNotification> findAllLatest(NotificationCommand.FindAllLatest findAllLatestQuery) {
        return notificationRepository.findAllLatest(findAllLatestQuery);
    }

    @Override
    public Notification create(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void delete(Notification notification) {
        notificationRepository.delete(notification);
    }

    @Override
    public void deleteAll(NotificationCommand.DeleteAll deleteAllCommand) {
        notificationRepository.deleteAll(deleteAllCommand);
    }
}
