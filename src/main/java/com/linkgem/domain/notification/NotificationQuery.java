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

        private LocalDateTime searchStartDate;

        @Builder
        public FindAll(Long userId, LocalDateTime searchStartDate) {
            this.userId = userId;
            this.searchStartDate = searchStartDate;
        }
    }

}
