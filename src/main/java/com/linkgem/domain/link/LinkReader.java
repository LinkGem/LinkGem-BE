package com.linkgem.domain.link;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LinkReader {

    Page<LinkInfo.Search> findAll(Long userId, Pageable pageable);

    Optional<Link> find(Long id, Long userId);

}
