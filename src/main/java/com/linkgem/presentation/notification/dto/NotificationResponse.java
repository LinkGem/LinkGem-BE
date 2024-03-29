package com.linkgem.presentation.notification.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.linkgem.domain.common.date.PastDay;
import com.linkgem.domain.notification.NotificationButtonAction;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NotificationResponse {

    private NotificationResponse() {
    }

    @Getter
    public static class NewNotificationInformation {
        private long count;
        private boolean hasNewNotification;

        public NewNotificationInformation(long count) {
            this.count = count;
            this.hasNewNotification = count > 0;
        }
    }

    @Getter
    public static class ButtonMain {

        private NotificationButtonAction buttonAction;

        private String buttonText;

        private String buttonValue;

        public ButtonMain(NotificationInfo.ButtonMain buttonMain) {
            this.buttonAction = buttonMain.getButtonAction();
            this.buttonText = buttonMain.getButtonText();
            this.buttonValue = buttonMain.getButtonValue();
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Main {
        private Long id;

        private NotificationType type;

        private Boolean isRead;

        private String content;

        private ButtonMain button;

        private LocalDateTime receivedDateTime;

        public static Main of(NotificationInfo.Main notification) {

            return Main.builder()
                .id(notification.getId())
                .type(notification.getType())
                .content(notification.getContent())
                .isRead(notification.isRead())
                .button(new ButtonMain(notification.getButton()))
                .receivedDateTime(notification.getReceivedDateTime())
                .build();
        }

        public static List<Main> ofs(List<NotificationInfo.Main> notifications) {
            return notifications.stream()
                .map(Main::of)
                .collect(Collectors.toList());
        }

        public String getPastDay() {

            if (receivedDateTime == null) {
                return "";
            }

            return PastDay.getPastDay(receivedDateTime);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class NotificationTypeResponse {
        private String type;
        private String name;

        public static NotificationTypeResponse of(NotificationType type) {
            return new NotificationTypeResponse(type.name(), type.getDescription());
        }

        public static List<NotificationTypeResponse> ofs(List<NotificationType> types) {
            return types.stream()
                .map(NotificationTypeResponse::of)
                .collect(Collectors.toList());
        }

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class LatestNotification {
        private NotificationType type;
        private long count;

        public static LatestNotification of(NotificationInfo.LatestNotification latestNotification) {
            return new LatestNotification(latestNotification.getType(), latestNotification.getCount());
        }

        public static List<LatestNotification> ofs(List<NotificationInfo.LatestNotification> latestNotifications) {
            return latestNotifications.stream()
                .map(LatestNotification::of)
                .collect(Collectors.toList());
        }
    }
}
