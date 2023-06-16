package com.linkgem.application.notification.service.create.factory;

import org.springframework.stereotype.Component;

import com.linkgem.domain.notification.NotificationButtonAction;
import com.linkgem.presentation.notification.cmd.NotificationCommand;
import com.linkgem.domain.notification.NotificationType;
import com.linkgem.domain.user.User;

@Component
public class NotificationCreateFactory {

    public NotificationCommand.Create createJoinNotification(User user) {
        return NotificationCommand.Create.builder()
            .type(NotificationType.MESSAGE)
            .content(String.format(NotificationMessage.JOIN.getMessage(), user.getNickname()))
            .receiverId(user.getId())
            .build();
    }

    public NotificationCommand.Create createEventNotification(User user) {
        return NotificationCommand.Create.builder()
            .type(NotificationType.EVENT)
            .buttonText("이벤트 참여하기")
            .buttonAction(NotificationButtonAction.MOVE_LINK)
            .buttonValue("https://forms.gle/rLTXHQTM46gsgZFg8")
            .content(NotificationMessage.EVENT20230227.getMessage())
            .receiverId(user.getId())
            .build();
    }
}
