package com.linkgem.domain.notification.service.delete;

import com.linkgem.domain.notification.NotificationCommand;

public interface NotificationDelete {

    void delete(NotificationCommand.Delete deleteCommand);
}
