package com.linkgem.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.domain.notification.service.create.NotificationCreator;
import com.linkgem.domain.notification.service.search.NotificationSearchService;
import com.linkgem.domain.notification.service.update.NotificationUpdater;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationFacade {
    private final NotificationSearchService notificationSearchService;
    private final NotificationUpdater notificationUpdater;
    private final NotificationCreator notificationCreator;

    public NotificationInfo.Main create(NotificationCommand.Create createCommand) {
        return notificationCreator.create(createCommand);
    }

    public Page<NotificationInfo.Main> findAll(NotificationQuery.Search searchQuery, Pageable pageable) {

        Page<NotificationInfo.Main> notifications = notificationSearchService.findAll(searchQuery, pageable);

        notificationUpdater.updateAllToRead(searchQuery.getUserId());

        return notifications;
    }

    public Long getUnReadNotificationCount(NotificationQuery.Search searchQuery) {
        return notificationSearchService.getUnReadNotificationCount(searchQuery);
    }
}
