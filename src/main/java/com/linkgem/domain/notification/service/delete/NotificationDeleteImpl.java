package com.linkgem.domain.notification.service.delete;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.notification.Notification;
import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.domain.notification.NotificationReader;
import com.linkgem.domain.notification.NotificationStore;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationDeleteImpl implements NotificationDelete {

    private final NotificationReader notificationReader;
    private final NotificationStore notificationStore;

    @Transactional
    @Override
    public void delete(NotificationCommand.Delete deleteCommand) {
        NotificationQuery.FindOne findOneQuery =
            NotificationQuery.FindOne.of(deleteCommand.getNotificationId(), deleteCommand.getUserId());

        Notification notification = notificationReader.findOne(findOneQuery)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND));

        notificationStore.delete(notification);
    }
}
