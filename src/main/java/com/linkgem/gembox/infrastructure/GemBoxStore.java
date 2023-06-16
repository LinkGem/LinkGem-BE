package com.linkgem.gembox.infrastructure;

import com.linkgem.domain.gembox.GemBox;

public interface GemBoxStore {
    GemBox create(GemBox gembox);

    void delete(GemBox gemBox);

    void deleteAllByUserId(Long userId);
}
