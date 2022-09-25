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
        private NotificationCategory category;
        private String emoticon;
        private String title;
        private String content;
        private ButtonAction buttonAction;
        private String buttonTitle;
        private String buttonValue;
        private Long receiverId;
        private Long senderId;

        public Notification toEntity() {

            return Notification.builder()
                .category(category)
                .emoticon(emoticon)
                .title(title)
                .content(content)
                .button(new Button(buttonAction, buttonTitle, buttonValue))
                .build();
        }

    }
}
