package com.linkgem.application.gembox.service.search;

import com.linkgem.domain.gembox.GemBoxInfo;
import com.linkgem.domain.gembox.GemBoxQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GemBoxSearchService {
    List<GemBoxInfo.Main> findAll(Long userId);

    Page<GemBoxInfo.Search> search(Long userId, Pageable pageable);

    GemBoxInfo.Main find(GemBoxQuery.SearchDetail searchDetail);
}
