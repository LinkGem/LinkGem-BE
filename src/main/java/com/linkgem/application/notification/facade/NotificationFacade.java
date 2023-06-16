package com.linkgem.application.notification.facade;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.linkgem.presentation.notification.cmd.NotificationCommand;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.application.notification.service.create.NotificationCreateService;
import com.linkgem.application.notification.service.delete.NotificationDeleteService;
import com.linkgem.application.notification.service.search.NotificationSearchService;
import com.linkgem.application.notification.service.update.NotificationUpdateService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationFacade {
    private final NotificationSearchService notificationSearchService;
    private final NotificationCreateService notificationCreateService;
    private final NotificationUpdateService notificationUpdateService;
    private final NotificationDeleteService notificationDeleteService;

    public NotificationInfo.Main create(NotificationCommand.Create createCommand) {
        return notificationCreateService.create(createCommand);
    }

    public Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable) {
        return notificationSearchService.findAll(findAllQuery, pageable);
    }

    public List<NotificationInfo.LatestNotification> findAllLatest(NotificationQuery.FindAllLatest findAllLatestQuery) {
        return notificationSearchService.findAllLatest(findAllLatestQuery);
    }

    public void readNotification(NotificationCommand.Read command) {
        notificationUpdateService.readNotification(command);
    }

    public void deleteNotification(NotificationCommand.Delete command) {
        notificationDeleteService.delete(command);
    }
}
