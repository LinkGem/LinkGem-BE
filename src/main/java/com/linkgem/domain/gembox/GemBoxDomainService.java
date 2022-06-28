package com.linkgem.domain.gembox;

public interface GemBoxDomainService {
    boolean isExisted(GemBox gemBox);

    boolean isFull(Long userId);
}
