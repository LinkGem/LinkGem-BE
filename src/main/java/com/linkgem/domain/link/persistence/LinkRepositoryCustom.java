package com.linkgem.domain.link.persistence;

import java.util.Optional;

import com.linkgem.domain.link.dto.LinkCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.link.domain.Link;
import com.linkgem.domain.link.dto.LinkInfo;

public interface LinkRepositoryCustom {
    Page<LinkInfo.Search> findAll(LinkCommand.SearchLinks searchLinks, Pageable pageable);

    Optional<Link> findOneJoinUser(Long id, Long userId);
}
