package com.linkgem.domain.link.persistence;

import java.util.List;
import java.util.Optional;

import com.linkgem.domain.link.domain.Link;
import com.linkgem.domain.link.dto.LinkCommand;
import com.linkgem.domain.link.dto.LinkInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LinkPersistence {

    Link create(Link link);

    Long delete(Link link);

    void deleteAllByUserId(Long userId);

    Page<LinkInfo.Search> findAll(LinkCommand.SearchLinks searchLinks, Pageable pageable);

    List<Link> findAllByUserId(Long userId);

    List<Link> findAllByGemBoxId(Long gemBoxId);

    Optional<Link> find(Long id, Long userId);

    Link get(Long id, Long userId);

    Optional<Link> findOneJoinUser(Long id, Long userId);

}
