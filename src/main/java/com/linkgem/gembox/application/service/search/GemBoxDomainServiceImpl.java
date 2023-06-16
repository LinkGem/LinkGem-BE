package com.linkgem.gembox.application.service.search;

import com.linkgem.domain.gembox.GemBox;
import com.linkgem.domain.gembox.GemBoxQuery;
import com.linkgem.gembox.infrastructure.GemBoxReader;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GemBoxDomainServiceImpl implements GemBoxDomainService {

    private final GemBoxReader gemBoxReader;

    @Override
    public boolean isExisted(GemBoxQuery.SearchDuplication searchDuplication) {
        return gemBoxReader.find(searchDuplication.getName(), searchDuplication.getUserId())
            .map(gemBox -> !gemBox.isEqual(searchDuplication.getId()))
            .orElse(false);
    }

    @Override
    public boolean isFull(Long userId) {
        return gemBoxReader.findAll(userId).size() >= GemBox.MAX_GEMBOX;
    }
}
