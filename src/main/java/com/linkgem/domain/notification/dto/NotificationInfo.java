package com.linkgem.domain.notification.dto;

import java.time.LocalDateTime;

import com.linkgem.domain.notification.domain.Notification;
import com.linkgem.domain.notification.domain.NotificationButton;
import com.linkgem.domain.notification.domain.NotificationButtonAction;
import com.linkgem.domain.notification.domain.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NotificationInfo {

    private NotificationInfo() {
    }

    @Getter
    public static class ButtonMain {

        private NotificationButtonAction buttonAction;

        private String buttonText;

        private String buttonValue;

        public ButtonMain(NotificationButton notificationButton) {
            this.buttonAction = notificationButton.getButtonAction();
            this.buttonText = notificationButton.getButtonText();
            this.buttonValue = notificationButton.getButtonValue();
        }
    }

    @Getter
    public static class Main {
        private Long id;

        private NotificationType type;

        private String content;

        private ButtonMain button;

        private boolean isRead;

        private LocalDateTime receivedDateTime;

        @Builder
        public Main(Long id, NotificationType type, String content,
                    NotificationButton button, boolean isRead, LocalDateTime receivedDateTime) {
            this.id = id;
            this.type = type;
            this.content = content;
            this.isRead = isRead;
            this.button = new ButtonMain(button);
            this.receivedDateTime = receivedDateTime;
        }

        public static Main of(Notification notification) {
            return Main.builder()
                .id(notification.getId())
                .type(notification.getType())
                .content(notification.getContent())
                .isRead(notification.isRead())
                .button(notification.getButton())
                .receivedDateTime(notification.getReceivedDateTime())
                .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class LatestNotification {
        private NotificationType type;
        private long count;
    }
}
