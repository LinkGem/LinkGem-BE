package com.linkgem.domain.notification.service.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.domain.notification.NotificationReader;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationUpdateImpl implements NotificationUpdate {

    private final NotificationReader notificationReader;

    @Transactional
    @Override
    public void readNotification(NotificationCommand.Read command) {

        NotificationQuery.FindOne findOneQuery =
            NotificationQuery.FindOne.of(command.getNotificationId(), command.getUserId());

        notificationReader.findOne(findOneQuery)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND))
            .read();
    }
}
