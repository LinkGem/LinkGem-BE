package com.linkgem.domain.notification.service.create;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.notification.Button;
import com.linkgem.domain.notification.Notification;
import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationStore;
import com.linkgem.domain.user.User;
import com.linkgem.domain.user.UserReader;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationCreatorImpl implements NotificationCreator {

    private final UserReader userReader;

    private final NotificationStore notificationStore;

    @Transactional
    @Override
    public NotificationInfo.Main create(NotificationCommand.Create createCommand) {

        User receiver = userReader.find(createCommand.getReceiverId())
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        User sender = createCommand.getSenderId() == null ? null :
            userReader.find(createCommand.getSenderId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Button button =
            new Button(createCommand.getButtonAction(), createCommand.getButtonTitle(), createCommand.getButtonValue());

        Notification notification = Notification.builder()
            .category(createCommand.getCategory())
            .emoticon(createCommand.getEmoticon())
            .title(createCommand.getTitle())
            .content(createCommand.getContent())
            .button(button)
            .receiver(receiver)
            .sender(sender)
            .build();

        return NotificationInfo.Main.of(notificationStore.create(notification));
    }

}
