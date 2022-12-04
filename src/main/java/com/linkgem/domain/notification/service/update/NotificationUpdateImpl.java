package com.linkgem.domain.notification.service.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationReader;
import com.linkgem.domain.notification.NotificationStore;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationUpdateImpl implements NotificationUpdate {

    private final NotificationReader notificationReader;

    private final NotificationStore notificationStore;

    @Transactional
    @Override
    public void readNotification(NotificationCommand.Read command) {
        notificationReader.find(command.getNotificationId())
            .filter(notification -> notification.isOwner(command.getUserId()))
            .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND))
            .read();
    }
}
