package com.linkgem.domain.notification.service.update;

import com.linkgem.domain.notification.NotificationCommand;

public interface NotificationUpdate {
    void readNotification(NotificationCommand.Read command);
}
