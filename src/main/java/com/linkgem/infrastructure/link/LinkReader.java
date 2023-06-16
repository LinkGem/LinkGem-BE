package com.linkgem.infrastructure.link;

import java.util.List;
import java.util.Optional;

import com.linkgem.domain.link.Link;
import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LinkReader {

    Page<LinkInfo.Search> findAll(LinkQuery.SearchLinks searchLinks, Pageable pageable);

    List<Link> findAllByUserId(Long userId);

    List<Link> findAllByGemBoxId(Long gemBoxId);

    Optional<Link> find(Long id, Long userId);

    Link get(Long id, Long userId);

    Optional<Link> findOneJoinUser(Long id, Long userId);

}
