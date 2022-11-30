package com.linkgem.domain.notification.service.create;

import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationInfo;

public interface NotificationCreate {
    NotificationInfo.Main create(NotificationCommand.Create createCommand);

}
