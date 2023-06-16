package com.linkgem.application.link.service.search;

import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LinkSearchService {
    Page<LinkInfo.Search> findAll(LinkQuery.SearchLinks searchLinks, Pageable pageable);

    LinkInfo.Detail findById(Long id, Long userId);
}
