package com.linkgem.presentation.notification.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.linkgem.domain.notification.ButtonAction;
import com.linkgem.domain.notification.NotificationCategory;
import com.linkgem.domain.notification.NotificationCommand;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class NotificationRequest {
    private NotificationRequest() {
    }

    @AllArgsConstructor
    @Getter
    public static class Create {

        @NotNull
        private NotificationCategory category;

        private String emoticon;

        private String title;

        @NotBlank
        private String content;

        @NotNull
        private ButtonAction buttonAction;

        @NotBlank
        private String buttonTitle;

        @NotBlank
        private String buttonValue;

        public NotificationCommand.Create toCommand(Long receiverId, Long senderId) {
            return NotificationCommand.Create.builder()
                .category(category)
                .emoticon(emoticon)
                .title(title)
                .content(content)
                .buttonAction(buttonAction)
                .buttonTitle(buttonTitle)
                .buttonValue(buttonValue)
                .receiverId(receiverId)
                .senderId(senderId)
                .build();
        }
    }
}