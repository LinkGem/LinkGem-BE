package com.linkgem.domain.notification;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.linkgem.domain.common.BaseEntity;
import com.linkgem.domain.user.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Notification extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(nullable = false, length = 1000)
    private String content;

    @Embedded
    private NotificationButton button = new NotificationButton();

    @Column(name = "is_read", nullable = false)
    private boolean isRead;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Builder
    public Notification(NotificationType type, String content, NotificationButton button, User receiver) {
        this.type = type;
        this.content = content;
        this.button = button;
        this.receiver = receiver;
        this.isRead = false;
    }

    public void updateToRead() {
        this.isRead = true;
    }
}
