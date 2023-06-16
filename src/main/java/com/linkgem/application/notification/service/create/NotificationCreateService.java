package com.linkgem.application.notification.service.create;

import com.linkgem.presentation.notification.cmd.NotificationCommand;
import com.linkgem.domain.notification.NotificationInfo;

public interface NotificationCreateService {
    NotificationInfo.Main create(NotificationCommand.Create createCommand);

}
