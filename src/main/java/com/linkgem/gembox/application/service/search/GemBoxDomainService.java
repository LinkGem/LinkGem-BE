package com.linkgem.gembox.application.service.search;

import com.linkgem.domain.gembox.GemBoxQuery;

public interface GemBoxDomainService {
    boolean isExisted(GemBoxQuery.SearchDuplication searchDuplication);

    boolean isFull(Long userId);
}
