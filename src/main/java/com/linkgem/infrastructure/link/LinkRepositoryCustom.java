package com.linkgem.infrastructure.link;

import java.util.Optional;

import com.linkgem.domain.link.LinkCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.link.Link;
import com.linkgem.domain.link.LinkInfo;

public interface LinkRepositoryCustom {
    Page<LinkInfo.Search> findAll(LinkCommand.SearchLinks searchLinks, Pageable pageable);

    Optional<Link> findOneJoinUser(Long id, Long userId);
}
