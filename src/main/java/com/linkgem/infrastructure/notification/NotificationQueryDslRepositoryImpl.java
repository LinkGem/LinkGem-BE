package com.linkgem.infrastructure.notification;

import static com.linkgem.domain.notification.QNotification.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.linkgem.domain.notification.Notification;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationQueryDslRepositoryImpl implements NotificationQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<NotificationInfo.Main> findAll(NotificationQuery.FindAll findAllQuery, Pageable pageable) {

        BooleanBuilder whereBuilder = getFindAllWhereQuery(findAllQuery);

        List<NotificationInfo.Main> results = queryFactory
            .select(Projections.constructor(
                NotificationInfo.Main.class,
                notification.id,
                notification.type,
                notification.content,
                notification.button,
                notification.isRead,
                notification.receivedDateTime
            ))
            .from(notification)
            .where(whereBuilder)
            .orderBy(notification.receivedDateTime.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(
            results, pageable, () -> this.getNotificationTotalCount(whereBuilder)
        );
    }

    @Override
    public Optional<Notification> findOne(NotificationQuery.FindOne findOneQuery) {
        return Optional.ofNullable(
            queryFactory
                .selectFrom(notification)
                .where(
                    notification.id.eq(findOneQuery.getNotificationId()),
                    notification.receiver.id.eq(findOneQuery.getReceiverId())
                )
                .fetchOne()
        );
    }

    private Long getNotificationTotalCount(BooleanBuilder whereBuilder) {
        return queryFactory.select(notification.count())
            .from(notification)
            .where(whereBuilder)
            .fetchOne();
    }

    private BooleanBuilder getFindAllWhereQuery(NotificationQuery.FindAll findAllQuery) {
        BooleanBuilder whereBuilder = new BooleanBuilder();

        if (findAllQuery.getIsRead() != null) {
            whereBuilder.and(notification.isRead.eq(findAllQuery.getIsRead()));
        }

        whereBuilder
            .and(notification.receiver.id.eq(findAllQuery.getUserId()))
            .and(notification.receivedDateTime.goe(findAllQuery.getSearchStartDateTime()))
        ;

        return whereBuilder;
    }

    @Override
    public List<NotificationInfo.LatestNotification> findAllLatest(NotificationQuery.FindAllLatest findAllLatestQuery) {
        return queryFactory.select(
                Projections.constructor(NotificationInfo.LatestNotification.class,
                    notification.type,
                    notification.count()
                ))
            .from(notification)
            .where(
                notification.isRead.isFalse(),
                notification.receiver.id.eq(findAllLatestQuery.getUserId()),
                notification.receivedDateTime.goe(findAllLatestQuery.getSearchStartDateTime())
            )
            .groupBy(notification.type)
            .fetch();

    }
}
