package com.linkgem.domain.link;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LinkSearchService {
    Page<LinkInfo.Search> findAll(LinkQuery.SearchLinks searchLinks, Pageable pageable);

    LinkInfo.Detail findById(Long id, Long userId);
}
