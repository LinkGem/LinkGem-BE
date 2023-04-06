package com.linkgem.domain.notification.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Embeddable
public class NotificationButton {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private NotificationButtonAction buttonAction;

    @Column(length = 50)
    private String buttonText;

    @Column(length = 100)
    private String buttonValue;

    @Builder
    private NotificationButton(NotificationButtonAction buttonAction, String buttonText, String buttonValue) {
        this.buttonAction = buttonAction;
        this.buttonText = buttonText;
        this.buttonValue = buttonValue;
    }

    public static NotificationButton empty() {
        return NotificationButton.builder()
            .buttonAction(NotificationButtonAction.NONE)
            .buttonText("")
            .buttonValue("")
            .build();
    }

}
