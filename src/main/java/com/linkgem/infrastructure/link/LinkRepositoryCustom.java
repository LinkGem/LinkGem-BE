package com.linkgem.infrastructure.link;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.link.LinkInfo;

public interface LinkRepositoryCustom {
    Page<LinkInfo.Search> findAll(Long userId, Pageable pageable);
}
