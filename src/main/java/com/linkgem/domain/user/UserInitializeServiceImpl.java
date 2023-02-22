package com.linkgem.domain.user;

import org.springframework.stereotype.Service;

import com.linkgem.domain.gembox.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxService;
import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.service.create.NotificationCreate;
import com.linkgem.domain.notification.service.create.factory.NotificationCreateFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserInitializeServiceImpl implements UserInitializeService {
    private final NotificationCreateFactory notificationCreateFactory;
    private final NotificationCreate notificationCreate;
    private final GemBoxService gemBoxService;

    @Override
    public void initialize(User user) {

        final Long userId = user.getId();

        //기본 잼박스 생성
        gemBoxService.create(GemBoxCommand.Create.createDefault(userId));

        //회원가입 환영 알림
        NotificationCommand.Create joinNotification = notificationCreateFactory.createJoinNotification(user);
        notificationCreate.create(joinNotification);

        //이벤트 알림
        NotificationCommand.Create eventNotification = notificationCreateFactory.createEventNotification(user);
        notificationCreate.create(eventNotification);
    }
}
