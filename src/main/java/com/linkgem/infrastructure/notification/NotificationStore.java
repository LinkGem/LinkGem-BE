package com.linkgem.infrastructure.notification;

import com.linkgem.domain.notification.Notification;
import com.linkgem.presentation.notification.cmd.NotificationCommand;

public interface NotificationStore {
    Notification create(Notification notification);

    void delete(Notification notification);

    void deleteAll(NotificationCommand.DeleteAll deleteAllCommand);

}
