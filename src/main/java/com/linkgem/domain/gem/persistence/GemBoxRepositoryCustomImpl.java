package com.linkgem.domain.gem.persistence;

import static com.linkgem.domain.gem.domain.QLink.*;

import java.util.List;

import com.linkgem.domain.gem.domain.QGemBox;
import com.linkgem.domain.gem.dto.GemBoxInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

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
                    QGemBox.gemBox.id,
                    QGemBox.gemBox.name,
                    QGemBox.gemBox.isDefault,
                    JPAExpressions.select(link.count())
                        .from(link)
                        .where(link.gemBox.id.eq(QGemBox.gemBox.id)),
                    new CaseBuilder().when(link.openGraph.imageUrl.isNull())
                        .then("")
                        .otherwise(link.openGraph.imageUrl)
                ))
            .from(QGemBox.gemBox)
            .leftJoin(link).on(link.gemBox.id.eq(QGemBox.gemBox.id))
            .where(QGemBox.gemBox.userId.eq(userId))
            .groupBy(QGemBox.gemBox.id)
            .orderBy(QGemBox.gemBox.createDate.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(results, pageable, () -> this.getTotalCount(userId));
    }

    private Long getTotalCount(Long userId) {
        return queryFactory
            .select(QGemBox.gemBox.count())
            .from(QGemBox.gemBox)
            .where(QGemBox.gemBox.userId.eq(userId))
            .fetchOne();
    }
}
