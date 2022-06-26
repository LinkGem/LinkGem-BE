package com.linkgem.domain.gembox;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GemBoxDomainServiceImpl implements GemBoxDomainService {

    private final GemBoxReader gemBoxReader;

    @Override
    public boolean isExisted(GemBox gemBox) {
        return gemBoxReader.find(gemBox.getName(), gemBox.getUserId())
            .isPresent();
    }

    @Override
    public boolean isFull(Long userId) {
        return gemBoxReader.findAll(userId).size() >= GemBox.MAX_GEMBOX;
    }
}
