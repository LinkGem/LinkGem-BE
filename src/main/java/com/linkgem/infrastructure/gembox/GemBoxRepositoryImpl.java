package com.linkgem.infrastructure.gembox;

import static com.linkgem.domain.gembox.QGemBox.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.gembox.GemBox;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GemBoxRepositoryImpl implements GemBoxRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * queryDsl 예제
     * @return
     */
    @Override
    public Page<GemBox> findAllByUserId(Long userId, Pageable pageable) {
        List<GemBox> contents = queryFactory
            .selectFrom(gemBox)
            // .where(gemBox.user.id.eq(userId))
            .fetch();

        return new PageImpl<>(contents, pageable, contents.size());
    }
}
