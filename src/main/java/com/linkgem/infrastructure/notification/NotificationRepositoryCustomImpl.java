package com.linkgem.infrastructure.notification;

import static com.linkgem.domain.notification.QNotification.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationRepositoryCustomImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<NotificationInfo.Main> findAll(NotificationQuery.Search searchQuery, Pageable pageable) {

        BooleanBuilder whereBuilder = getFindAllWhereQuery(searchQuery);

        List<NotificationInfo.Main> results = queryFactory
            .select(Projections.constructor(
                NotificationInfo.Main.class,
                notification.id,
                notification.category,
                notification.emoticon,
                notification.title,
                notification.content,
                notification.button,
                notification.isRead,
                notification.createDate
            ))
            .from(notification)
            .where(whereBuilder)
            .orderBy(notification.createDate.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(
            results, pageable, () -> this.findAllCount(whereBuilder)
        );
    }

    private Long findAllCount(BooleanBuilder whereBuilder) {
        return queryFactory.select(notification.count())
            .from(notification)
            .where(whereBuilder)
            .fetchOne();
    }

    private BooleanBuilder getFindAllWhereQuery(NotificationQuery.Search searchQuery) {
        BooleanBuilder whereBuilder = new BooleanBuilder();

        whereBuilder
            .and(notification.receiver.id.eq(searchQuery.getUserId()))
            .and(notification.isDeleted.isFalse())
            .and(notification.createDate.between(searchQuery.getSearchStartDate(), LocalDateTime.now()))
        ;

        return whereBuilder;
    }

    @Override
    public Long getUnReadNotificationCount(NotificationQuery.Search searchQuery) {
        return queryFactory.select(notification.count())
            .from(notification)
            .where(
                notification.receiver.id.eq(searchQuery.getUserId()),
                notification.createDate.between(searchQuery.getSearchStartDate(), LocalDateTime.now()),
                notification.isDeleted.isFalse(),
                notification.isRead.isFalse()
            )
            .fetchOne();
    }
}
