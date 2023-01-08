package com.linkgem.infrastructure.link;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.link.Link;
import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkQuery;

public interface LinkRepositoryCustom {
    Page<LinkInfo.Search> findAll(LinkQuery.SearchLinks searchLinks, Pageable pageable);

    Optional<Link> findOneJoinUser(Long id, Long userId);
}
