package com.linkgem.domain.notification.service;

import com.linkgem.domain.notification.domain.Notification;
import com.linkgem.domain.notification.domain.NotificationButton;
import com.linkgem.domain.notification.domain.NotificationMessage;
import com.linkgem.domain.notification.domain.NotificationType;
import com.linkgem.domain.notification.dto.NotificationCommand;
import com.linkgem.domain.notification.dto.NotificationInfo;
import com.linkgem.domain.notification.persistence.NotificationPersistence;
import com.linkgem.domain.user.domain.User;
import com.linkgem.domain.user.persistence.UserPersistence;
import com.linkgem.domain.common.exception.BusinessException;
import com.linkgem.domain.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final UserPersistence userPersistence;

    private final NotificationPersistence notificationPersistence;

    @Transactional
    @Override
    public NotificationInfo.Main create(NotificationCommand.Create createCommand) {

        User receiver = userPersistence.find(createCommand.getReceiverId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        NotificationButton notificationButton = createNotificationButton(createCommand)
                .orElseGet(NotificationButton::empty);

        Notification notification = Notification.builder()
                .type(createCommand.getType())
                .content(createCommand.getContent())
                .receiver(receiver)
                .button(notificationButton)
                .build();

        return NotificationInfo.Main.of(notificationPersistence.create(notification));
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

    @Transactional
    @Override
    public void delete(NotificationCommand.Delete deleteCommand) {
        NotificationCommand.FindOne findOneQuery =
                NotificationCommand.FindOne.of(deleteCommand.getNotificationId(), deleteCommand.getUserId());

        Notification notification = notificationPersistence.findOne(findOneQuery)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND));

        notificationPersistence.delete(notification);
    }

    @Override
    public void deleteAll(NotificationCommand.DeleteAll deleteCommand) {
        notificationPersistence.deleteAll(deleteCommand);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<NotificationInfo.Main> findAll(NotificationCommand.FindAll findAllQuery, Pageable pageable) {
        return notificationPersistence.findAll(findAllQuery, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationInfo.LatestNotification> findAllLatest(NotificationCommand.FindAllLatest findAllLatestQuery) {
        return notificationPersistence.findAllLatest(findAllLatestQuery);
    }

    @Transactional
    @Override
    public void readNotification(NotificationCommand.Read command) {

        NotificationCommand.FindOne findOneQuery =
                NotificationCommand.FindOne.of(command.getNotificationId(), command.getUserId());

        notificationPersistence.findOne(findOneQuery)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND))
                .read();
    }

    public NotificationCommand.Create createJoinNotification(User user) {
        return NotificationCommand.Create.builder()
                .type(NotificationType.MESSAGE)
                .content(String.format(NotificationMessage.JOIN.getMessage(), user.getNickname()))
                .receiverId(user.getId())
                .build();
    }
}
