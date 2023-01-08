package com.linkgem.domain.gembox;

public interface GemBoxDomainService {
    boolean isExisted(GemBoxQuery.SearchDuplication searchDuplication);

    boolean isFull(Long userId);
}
