package com.linkgem.domain.notification;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

public class NotificationInfo {

    private NotificationInfo() {
    }

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

        private boolean isRead;

        private LocalDateTime createDate;

        @Builder
        public Main(Long id, NotificationCategory category, String emoticon, String title, String content,
            Button button, boolean isRead, LocalDateTime createDate) {
            this.id = id;
            this.category = category;
            this.emoticon = emoticon;
            this.title = title;
            this.content = content;
            this.buttonAction = button.getButtonAction();
            this.buttonTitle = button.getButtonTitle();
            this.buttonValue = button.getButtonValue();
            this.isRead = isRead;
            this.createDate = createDate;
        }

        public static Main of(Notification notification) {
            return Main.builder()
                .id(notification.getId())
                .category(notification.getCategory())
                .emoticon(notification.getEmoticon())
                .title(notification.getTitle())
                .content(notification.getContent())
                .button(notification.getButton())
                .createDate(notification.getCreateDate())
                .build();
        }
    }
}
