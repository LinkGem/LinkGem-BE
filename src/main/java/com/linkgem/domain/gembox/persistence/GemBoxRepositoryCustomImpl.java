package com.linkgem.domain.gembox.persistence;

import static com.linkgem.domain.gembox.domain.QGemBox.*;
import static com.linkgem.domain.link.domain.QLink.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.linkgem.domain.gembox.dto.GemBoxInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GemBoxRepositoryCustomImpl implements GemBoxRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GemBoxInfo.Search> search(Long userId, Pageable pageable) {
        List<GemBoxInfo.Search> results = queryFactory
            .select(
                Projections.constructor(
                    GemBoxInfo.Search.class,
                    gemBox.id,
                    gemBox.name,
                    gemBox.isDefault,
                    JPAExpressions.select(link.count())
                        .from(link)
                        .where(link.gemBox.id.eq(gemBox.id)),
                    new CaseBuilder().when(link.openGraph.imageUrl.isNull())
                        .then("")
                        .otherwise(link.openGraph.imageUrl)
                ))
            .from(gemBox)
            .leftJoin(link).on(link.gemBox.id.eq(gemBox.id))
            .where(gemBox.userId.eq(userId))
            .groupBy(gemBox.id)
            .orderBy(gemBox.createDate.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(results, pageable, () -> this.getTotalCount(userId));
    }

    private Long getTotalCount(Long userId) {
        return queryFactory
            .select(gemBox.count())
            .from(gemBox)
            .where(gemBox.userId.eq(userId))
            .fetchOne();
    }
}
