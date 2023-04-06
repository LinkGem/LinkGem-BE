package com.linkgem.domain.notification.persistence;

import java.util.List;
import java.util.Optional;

import com.linkgem.domain.notification.domain.Notification;
import com.linkgem.domain.notification.dto.NotificationCommand;
import com.linkgem.domain.notification.dto.NotificationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationPersistence {
    Page<NotificationInfo.Main> findAll(NotificationCommand.FindAll findAllQuery, Pageable pageable);

    Optional<Notification> findOne(NotificationCommand.FindOne findOneQuery);

    List<NotificationInfo.LatestNotification> findAllLatest(NotificationCommand.FindAllLatest findAllLatestQuery);

    Notification create(Notification notification);

    void delete(Notification notification);

    void deleteAll(NotificationCommand.DeleteAll deleteAllCommand);
}