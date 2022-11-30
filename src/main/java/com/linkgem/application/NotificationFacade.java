package com.linkgem.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.domain.notification.service.create.NotificationCreate;
import com.linkgem.domain.notification.service.search.NotificationSearchService;
import com.linkgem.domain.notification.service.update.NotificationUpdate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationFacade {
    private final NotificationSearchService notificationSearchService;
    private final NotificationUpdate notificationUpdate;
    private final NotificationCreate notificationCreate;

    public NotificationInfo.Main create(NotificationCommand.Create createCommand) {
        return notificationCreate.create(createCommand);
    }

    public Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable) {
        return notificationSearchService.findAll(findAllQuery, pageable);
    }

    public Long getUnReadNotificationCount(NotificationQuery.FindAll findAllQuery) {
        return notificationSearchService.getUnReadNotificationCount(findAllQuery);
    }
}
