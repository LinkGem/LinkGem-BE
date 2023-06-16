package com.linkgem.application.notification.service.delete;

import com.linkgem.presentation.notification.cmd.NotificationCommand;

public interface NotificationDeleteService {

    void delete(NotificationCommand.Delete deleteCommand);

    void deleteAll(NotificationCommand.DeleteAll deleteCommand);
}
