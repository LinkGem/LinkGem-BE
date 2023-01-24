package com.linkgem.infrastructure.link;

import static com.linkgem.domain.link.QLink.*;
import static com.linkgem.domain.user.QUser.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.linkgem.domain.link.Link;
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
                link
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

    @Override
    public Optional<Link> findOneJoinUser(Long id, Long userId) {
        return Optional.ofNullable(
            queryFactory.selectFrom(link)
                .join(link.user).fetchJoin()
                .where(
                    link.id.eq(id),
                    link.user.id.eq(userId)
                )
                .fetchOne()
        );
    }

    private BooleanBuilder createWhereBuilder(LinkQuery.SearchLinks searchLinks) {
        BooleanBuilder whereBuilder = new BooleanBuilder();
        whereBuilder.and(link.user.id.eq(searchLinks.getUserId()));

        if (searchLinks.getGemBoxId() != null) {
            whereBuilder.and(link.gemBox.id.eq(searchLinks.getGemBoxId()));
        }

        if (Objects.nonNull(searchLinks.getIsFavorites())) {
            whereBuilder.and(link.isFavorites.eq(searchLinks.getIsFavorites()));
        }

        if (Objects.nonNull(searchLinks.getHasMemo())) {
            whereBuilder.and(searchLinks.getHasMemo() ? link.memo.isNotEmpty() : link.memo.isEmpty());
        }

        if (Objects.nonNull(searchLinks.getIsDefault())) {
            whereBuilder.and(link.gemBox.isDefault.eq(searchLinks.getIsDefault()));
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
