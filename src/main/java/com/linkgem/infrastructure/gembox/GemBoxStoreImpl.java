package com.linkgem.infrastructure.gembox;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.gembox.GemBox;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class GemBoxStoreImpl implements GemBoxStore {

    private final GemBoxRepository gemBoxRepository;

    @Override
    public GemBox create(GemBox gembox) {
        return gemBoxRepository.save(gembox);
    }

    @Override
    public void delete(GemBox gemBox) {
        gemBoxRepository.delete(gemBox);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        gemBoxRepository.deleteAllByUserId(userId);
    }
}
