package com.linkgem.domain.commonlink.persistence;

import static com.linkgem.domain.commonlink.domain.QCommonLink.*;

import java.util.List;

import com.linkgem.domain.commonlink.dto.CommonLinkCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.linkgem.domain.commonlink.dto.CommonLinkInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CommonLinkRepositoryCustomImpl implements CommonLinkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CommonLinkInfo.Main> findAll(CommonLinkCommand.FindAll findAllQuery, Pageable pageable) {

        List<CommonLinkInfo.Main> results = queryFactory.select(
                Projections.constructor(
                    CommonLinkInfo.Main.class,
                    commonLink
                ))
            .from(commonLink)
            .orderBy(commonLink.createDate.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(results, pageable, this::getCommonLinkTotalCount);
    }

    private Long getCommonLinkTotalCount() {
        return queryFactory
            .select(commonLink.count())
            .from(commonLink)
            .fetchOne();
    }
}
