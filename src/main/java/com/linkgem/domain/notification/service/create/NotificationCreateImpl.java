package com.linkgem.domain.notification.service.create;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.notification.Notification;
import com.linkgem.domain.notification.NotificationButton;
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
public class NotificationCreateImpl implements NotificationCreate {

    private final UserReader userReader;

    private final NotificationStore notificationStore;

    @Transactional
    @Override
    public NotificationInfo.Main create(NotificationCommand.Create createCommand) {

        User receiver = userReader.find(createCommand.getReceiverId())
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        NotificationButton notificationButton = createNotificationButton(createCommand)
            .orElseGet(NotificationButton::empty);

        Notification notification = Notification.builder()
            .type(createCommand.getType())
            .content(createCommand.getContent())
            .receiver(receiver)
            .button(notificationButton)
            .build();

        return NotificationInfo.Main.of(notificationStore.create(notification));
    }

    private Optional<NotificationButton> createNotificationButton(NotificationCommand.Create createCommand) {
        if (createCommand.hasButton()) {
            return Optional.of(NotificationButton.builder()
                .buttonAction(createCommand.getButtonAction())
                .buttonText(createCommand.getButtonText())
                .buttonValue(createCommand.getButtonValue())
                .build());
        }

        return Optional.empty();
    }

}
