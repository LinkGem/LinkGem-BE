package com.linkgem.infrastructure.link;

import static com.linkgem.domain.link.QLink.*;
import static com.linkgem.domain.user.QUser.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.linkgem.domain.link.LinkInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LinkRepositoryCustomImpl implements LinkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<LinkInfo.Search> findAll(Long userId, Pageable pageable) {
        List<LinkInfo.Search> results = queryFactory
            .select(Projections.constructor(
                LinkInfo.Search.class,
                link.id,
                link.memo,
                link.url,
                link.openGraph.title,
                link.openGraph.description,
                link.openGraph.imageUrl,
                link.user.id.as("userId"),
                link.user.nickName,
                link.createDate,
                link.updateDate
            ))
            .from(link)
            .join(link.user, user)
            .where(link.user.id.eq(userId))
            .orderBy(link.createDate.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(results, pageable, () -> getTotal(userId));
    }

    private Long getTotal(Long userId) {
        return queryFactory
            .select(link.count())
            .from(link)
            .where(link.user.id.eq(userId))
            .fetchOne();
    }
}
