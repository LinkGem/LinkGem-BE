package com.linkgem.domain.notification.service.create.factory;

import org.springframework.stereotype.Component;

import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationType;
import com.linkgem.domain.user.User;

@Component
public class NotificationCreateFactory {

    public NotificationCommand.Create createJoinNotification(User user) {
        return NotificationCommand.Create.builder()
            .type(NotificationType.MESSAGE)
            .content(String.format(NotificationMessage.JOIN.getMessage(), user.getName()))
            .receiverId(user.getId())
            .build();
    }
}
