package com.linkgem.presentation.notification.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.linkgem.domain.notification.NotificationButtonAction;
import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class NotificationRequest {
    private NotificationRequest() {
    }

    @AllArgsConstructor
    @Getter
    public static class Create {

        @NotNull
        private NotificationType type;

        @NotBlank
        private String content;

        private NotificationButtonAction buttonAction;

        private String buttonText;

        private String buttonValue;

        public NotificationCommand.Create toCommand(Long receiverId) {
            return NotificationCommand.Create.builder()
                .type(this.type)
                .content(this.content)
                .buttonAction(this.buttonAction)
                .buttonText(this.buttonText)
                .buttonValue(this.buttonValue)
                .receiverId(receiverId)
                .build();
        }
    }

    @AllArgsConstructor
    @Getter
    public static class FindAll {
        private Boolean isRead;
        private NotificationType type;
    }
}
