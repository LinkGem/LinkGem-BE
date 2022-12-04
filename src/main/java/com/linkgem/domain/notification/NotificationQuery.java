package com.linkgem.domain.notification;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class NotificationQuery {
    private NotificationQuery() {
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class FindOne {
        private long notificationId;
        private long receiverId;

        public static NotificationQuery.FindOne of(long notificationId, Long receiverId) {
            return new NotificationQuery.FindOne(notificationId, receiverId);
        }
    }

    @Getter
    public static class FindAll {
        private Long userId;

        private LocalDateTime searchStartDateTime;

        private Boolean isRead;

        @Builder
        public FindAll(Long userId, LocalDateTime searchStartDateTime, Boolean isRead) {
            this.userId = userId;
            this.searchStartDateTime = searchStartDateTime;
            this.isRead = isRead;
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
