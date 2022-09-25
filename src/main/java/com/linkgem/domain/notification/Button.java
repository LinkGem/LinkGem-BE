package com.linkgem.domain.notification;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Button {
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ButtonAction buttonAction;

    @Column(length = 50)
    private String buttonTitle;

    @Column(length = 100)
    private String buttonValue;

    public Button(ButtonAction buttonAction, String buttonTitle, String buttonValue) {
        this.buttonAction = buttonAction;
        this.buttonTitle = buttonTitle;
        this.buttonValue = buttonValue;
    }

}
