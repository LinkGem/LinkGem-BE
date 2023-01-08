package com.linkgem.infrastructure.notification;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.infrastructure.config.TestQueryDslConfig;

@Sql(value = {"classpath:sql/user.sql", "classpath:sql/notification.sql"})
@Import(TestQueryDslConfig.class)
@DataJpaTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @DisplayName("알림 목록을 조회한다")
    @Test
    void 알림_목록_조회() {
        NotificationQuery.FindAll findAll = NotificationQuery.FindAll.builder()
            .userId(1L)
            .searchStartDateTime(LocalDateTime.now().minusDays(1))
            .build();

        Page<NotificationInfo.Main> notifications = notificationRepository.findAll(findAll, Pageable.ofSize(20));
        Assertions.assertEquals(2L, notifications.getTotalElements());
    }

}
