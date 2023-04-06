package com.linkgem.domain.notification.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkgem.domain.notification.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {
}
