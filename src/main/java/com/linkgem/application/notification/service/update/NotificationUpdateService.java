package com.linkgem.application.notification.service.update;

import com.linkgem.presentation.notification.cmd.NotificationCommand;

public interface NotificationUpdateService {
    void readNotification(NotificationCommand.Read command);
}
