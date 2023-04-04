package com.linkgem.domain.notification;

import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    NotificationInfo.Main create(NotificationCommand.Create createCommand);

    NotificationCommand.Create createJoinNotification(User user);

    Page<NotificationInfo.Main> findAll(NotificationCommand.FindAll findAllQuery, Pageable pageable);

    List<NotificationInfo.LatestNotification> findAllLatest(NotificationCommand.FindAllLatest findAllLatestQuery);

    void readNotification(NotificationCommand.Read command);

    void delete(NotificationCommand.Delete deleteCommand);

    void deleteAll(NotificationCommand.DeleteAll deleteCommand);
}
