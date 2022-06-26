package com.linkgem.infrastructure.gembox;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.gembox.GemBox;

public interface GemBoxRepositoryCustom {
    Page<GemBox> findAllByUserId(Long userId, Pageable pageable);

}
