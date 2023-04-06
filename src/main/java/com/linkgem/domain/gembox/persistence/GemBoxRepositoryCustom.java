package com.linkgem.domain.gembox.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.gembox.dto.GemBoxInfo;

public interface GemBoxRepositoryCustom {
    Page<GemBoxInfo.Search> search(Long userId, Pageable pageable);

}
