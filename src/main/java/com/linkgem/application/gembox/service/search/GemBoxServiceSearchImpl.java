package com.linkgem.application.gembox.service.search;

import com.linkgem.domain.gembox.*;
import com.linkgem.infrastructure.gembox.GemBoxReader;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GemBoxServiceSearchImpl implements GemBoxSearchService {

    private final GemBoxReader gemBoxReader;

    @Override
    public List<GemBoxInfo.Main> findAll(Long userId) {
        return gemBoxReader.findAll(userId)
                .stream()
                .map(GemBoxInfo.Main::of)
                .collect(Collectors.toList());
    }

    @Override
    public Page<GemBoxInfo.Search> search(Long userId, Pageable pageable) {
        return gemBoxReader.search(userId, pageable);
    }

    @Override
    public GemBoxInfo.Main find(GemBoxQuery.SearchDetail searchDetail) {
        return gemBoxReader.find(searchDetail.getId(), searchDetail.getUserId())
                .map(GemBoxInfo.Main::of)
                .orElseThrow(() -> new BusinessException(ErrorCode.GEMBOX_NOT_FOUND));
    }
}
