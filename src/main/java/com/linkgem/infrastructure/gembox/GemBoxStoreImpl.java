package com.linkgem.infrastructure.gembox;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.gembox.GemBox;
import com.linkgem.domain.gembox.GemBoxStore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class GemBoxStoreImpl implements GemBoxStore {

    private final GemBoxRepository gemBoxRepository;

    @Override
    public GemBox create(GemBox gembox) {
        return gemBoxRepository.save(gembox);
    }
}
