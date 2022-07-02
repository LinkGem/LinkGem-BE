package com.linkgem.domain.gembox;

public interface GemBoxStore {
    GemBox create(GemBox gembox);

    void delete(GemBox gemBox);
}
