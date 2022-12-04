package com.linkgem.domain.notification;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

public class NotificationQuery {
    private NotificationQuery() {
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

}
