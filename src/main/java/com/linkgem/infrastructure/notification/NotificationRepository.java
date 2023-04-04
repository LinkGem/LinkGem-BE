package com.linkgem.infrastructure.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkgem.domain.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {
}
