package com.linkgem.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.domain.notification.service.create.NotificationCreate;
import com.linkgem.domain.notification.service.search.NotificationSearch;
import com.linkgem.domain.notification.service.update.NotificationUpdate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationFacade {
    private final NotificationSearch notificationSearch;
    private final NotificationCreate notificationCreate;
    private final NotificationUpdate notificationUpdate;

    public NotificationInfo.Main create(NotificationCommand.Create createCommand) {
        return notificationCreate.create(createCommand);
    }

    public Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable) {
        return notificationSearch.findAll(findAllQuery, pageable);
    }

    public List<NotificationInfo.LatestNotification> findAllLatest(NotificationQuery.FindAllLatest findAllLatestQuery) {
        return notificationSearch.findAllLatest(findAllLatestQuery);
    }

    public void readNotification(NotificationCommand.Read command) {
        notificationUpdate.readNotification(command);
    }
}
