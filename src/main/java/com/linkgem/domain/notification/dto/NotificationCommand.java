package com.linkgem.domain.notification.dto;

import com.linkgem.domain.notification.domain.NotificationButtonAction;
import com.linkgem.domain.notification.domain.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class FindOne {
        private long notificationId;
        private long receiverId;

        public static NotificationCommand.FindOne of(long notificationId, Long receiverId) {
            return new NotificationCommand.FindOne(notificationId, receiverId);
        }
    }

    @Getter
    public static class FindAll {
        private Long userId;

        private LocalDateTime searchStartDateTime;

        private Boolean isRead;

        private NotificationType type;

        @Builder
        public FindAll(Long userId, LocalDateTime searchStartDateTime, Boolean isRead, NotificationType type) {
            this.userId = userId;
            this.searchStartDateTime = searchStartDateTime;
            this.isRead = isRead;
            this.type = type;
        }
    }

    @Getter
    public static class FindAllLatest {
        private Long userId;

        private LocalDateTime searchStartDateTime;

        @Builder
        public FindAllLatest(Long userId, LocalDateTime searchStartDateTime) {
            this.userId = userId;
            this.searchStartDateTime = searchStartDateTime;
        }
    }
}
