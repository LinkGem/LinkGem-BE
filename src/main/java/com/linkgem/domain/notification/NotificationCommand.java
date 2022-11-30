package com.linkgem.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
}
