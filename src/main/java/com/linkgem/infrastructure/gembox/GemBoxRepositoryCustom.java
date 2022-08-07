package com.linkgem.infrastructure.gembox;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.gembox.GemBoxInfo;

public interface GemBoxRepositoryCustom {
    Page<GemBoxInfo.Search> search(Long userId, Pageable pageable);

}
