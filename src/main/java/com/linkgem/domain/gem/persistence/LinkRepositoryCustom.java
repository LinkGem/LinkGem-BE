package com.linkgem.domain.gem.persistence;

import java.util.Optional;

import com.linkgem.domain.gem.dto.LinkCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.gem.domain.Link;
import com.linkgem.domain.gem.dto.LinkInfo;

public interface LinkRepositoryCustom {
    Page<LinkInfo.Search> findAll(LinkCommand.SearchLinks searchLinks, Pageable pageable);

    Optional<Link> findOneJoinUser(Long id, Long userId);
}
