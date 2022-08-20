package com.linkgem.domain.link;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LinkReader {

    Page<LinkInfo.Search> findAll(LinkQuery.SearchLinks searchLinks, Pageable pageable);

    Optional<Link> find(Long id, Long userId);

    Link get(Long id, Long userId);

    Optional<Link> findOneJoinUser(Long id, Long userId);

}
