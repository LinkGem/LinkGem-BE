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
    private NotificationCategory category;

    private String emoticon;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Embedded
    private Button button;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = true)
    private User sender;

    @Builder
    public Notification(NotificationCategory category, String emoticon, String title, String content,
        Button button, User receiver, User sender) {
        this.category = category;
        this.emoticon = emoticon;
        this.title = title;
        this.content = content;
        this.button = button;
        this.receiver = receiver;
        this.sender = sender;
        this.isRead = false;
        this.isDeleted = false;
    }

    public void updateToRead() {
        this.isRead = true;
    }

    public void updateToDeleted() {
        this.isDeleted = true;
    }
}
