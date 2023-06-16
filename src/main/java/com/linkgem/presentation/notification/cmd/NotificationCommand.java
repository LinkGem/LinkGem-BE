package com.linkgem.presentation.notification.cmd;

import com.linkgem.domain.notification.NotificationButtonAction;
import com.linkgem.domain.notification.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NotificationCommand {

    private NotificationCommand() {
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Create {
        private NotificationType type;
        private String content;
        private NotificationButtonAction buttonAction;
        private String buttonText;
        private String buttonValue;
        private Long receiverId;

        public boolean hasButton() {
            return buttonAction != null && buttonText != null && buttonValue != null;
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Read {
        private long notificationId;
        private long userId;

        public static NotificationCommand.Read of(long notificationId, long userId) {
            return new NotificationCommand.Read(notificationId, userId);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Delete {
        private long notificationId;
        private long userId;

        public static NotificationCommand.Delete of(long notificationId, long userId) {
            return new NotificationCommand.Delete(notificationId, userId);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class DeleteAll {
        private long userId;

        public static NotificationCommand.DeleteAll of(long userId) {
            return new NotificationCommand.DeleteAll(userId);
        }
    }

}
