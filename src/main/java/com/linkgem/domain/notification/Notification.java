package com.linkgem.domain.notification;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.linkgem.domain.user.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Notification {

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @CreatedDate
    @ColumnDefault("current_timestamp()")
    @Column(name = "received_date_time")
    private LocalDateTime receivedDateTime;

    @Builder
    public Notification(NotificationType type, String content, NotificationButton button, User receiver) {

        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(content, "content");
        Objects.requireNonNull(receiver, "receiver");

        this.type = type;
        this.content = content;
        this.button = button;
        this.receiver = receiver;
        this.isRead = false;
    }

    public void read() {
        this.isRead = true;
    }

    public boolean isOwner(long userId) {

        if (this.receiver == null) {
            return false;
        }

        return userId == this.receiver.getId();
    }
}
