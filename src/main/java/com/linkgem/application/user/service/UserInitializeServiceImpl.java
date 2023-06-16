package com.linkgem.application.user.service;

import com.linkgem.domain.user.User;
import org.springframework.stereotype.Service;

import com.linkgem.application.gembox.service.GemBoxService;
import com.linkgem.presentation.notification.cmd.NotificationCommand;
import com.linkgem.application.notification.service.create.NotificationCreateService;
import com.linkgem.application.notification.service.create.factory.NotificationCreateFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserInitializeServiceImpl implements UserInitializeService {
    private final NotificationCreateFactory notificationCreateFactory;
    private final NotificationCreateService notificationCreateService;
    private final GemBoxService gemBoxService;

    @Override
    public void initialize(User user) {

        final Long userId = user.getId();

        //회원가입 환영 알림
        NotificationCommand.Create joinNotification = notificationCreateFactory.createJoinNotification(user);
        notificationCreateService.create(joinNotification);
    }
}
