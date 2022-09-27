package com.linkgem.domain.notification.service.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.notification.NotificationReader;
import com.linkgem.domain.notification.NotificationStore;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationUpdaterImpl implements NotificationUpdater {

    private final NotificationReader notificationReader;

    private final NotificationStore notificationStore;

    @Transactional
    @Override
    public void updateToRead(Long notificationId) {
        notificationReader.find(notificationId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND))
            .updateToRead()
        ;

    }

    @Transactional
    @Override
    public void updateToDeleted(Long notificationId) {
        notificationReader.find(notificationId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND))
            .updateToDeleted();
    }

    @Transactional
    @Override
    public long updateAllToRead(Long userId) {
        return notificationStore.updateAllToRead(userId);
    }

}
