package com.linkgem.infrastructure.link;

import static com.linkgem.domain.link.QLink.*;
import static com.linkgem.domain.user.QUser.*;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkQuery;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LinkRepositoryCustomImpl implements LinkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<LinkInfo.Search> findAll(LinkQuery.SearchLinks searchLinks, Pageable pageable) {

        BooleanBuilder whereBuilder = this.createWhereBuilder(searchLinks);

        List<LinkInfo.Search> results = queryFactory
            .select(Projections.constructor(
                LinkInfo.Search.class,
                link.id,
                link.memo,
                link.url,
                link.openGraph.title,
                link.openGraph.description,
                link.openGraph.imageUrl,
                link.isFavorites,
                link.gemBox.id,
                link.user.id.as("userId"),
                link.user.nickname,
                link.createDate,
                link.updateDate
            ))
            .from(link)
            .join(link.user, user)
            .where(whereBuilder)
            .orderBy(link.createDate.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(results, pageable, () -> this.getLinkTotalCount(whereBuilder));
    }

    private BooleanBuilder createWhereBuilder(LinkQuery.SearchLinks searchLinks) {
        BooleanBuilder whereBuilder = new BooleanBuilder();
        whereBuilder.and(link.user.id.eq(searchLinks.getUserId()));

        if (searchLinks.getGemBoxId() == null) {
            if (searchLinks.getHasGembox() != null) {
                whereBuilder.and(searchLinks.getHasGembox() ? link.gemBox.id.isNotNull() : link.gemBox.id.isNull());
            }
        } else {
            whereBuilder.and(link.gemBox.id.eq(searchLinks.getGemBoxId()));
        }

        if (Objects.nonNull(searchLinks.getIsFavorites())) {
            whereBuilder.and(link.isFavorites.eq(searchLinks.getIsFavorites()));
        }
        return whereBuilder;
    }

    private Long getLinkTotalCount(BooleanBuilder whereBuilder) {
        return queryFactory
            .select(link.count())
            .from(link)
            .where(whereBuilder)
            .fetchOne();
    }
}
