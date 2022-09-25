package com.linkgem.presentation.notification.dto;

import java.time.LocalDateTime;

import com.linkgem.domain.common.date.PastDay;
import com.linkgem.domain.notification.ButtonAction;
import com.linkgem.domain.notification.NotificationCategory;
import com.linkgem.domain.notification.NotificationInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Main {
        private Long id;

        private NotificationCategory category;

        private String emoticon;

        private String title;

        private String content;

        private ButtonAction buttonAction;

        private String buttonTitle;

        private String buttonValue;

        private LocalDateTime createDate;

        public static Main of(NotificationInfo.Main notification) {
            return Main.builder()
                .id(notification.getId())
                .category(notification.getCategory())
                .emoticon(notification.getEmoticon())
                .title(notification.getTitle())
                .content(notification.getContent())
                .buttonAction(notification.getButtonAction())
                .buttonTitle(notification.getButtonTitle())
                .buttonValue(notification.getButtonValue())
                .createDate(notification.getCreateDate())
                .build();
        }

        public String getPastDay() {

            if (createDate == null) {
                return "";
            }

            return PastDay.getPastDay(createDate);
        }

    }
}
