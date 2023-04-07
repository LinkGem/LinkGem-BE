package com.linkgem.domain.gem.service;

import com.linkgem.domain.gem.dto.LinkCommand;
import com.linkgem.domain.gem.dto.LinkInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LinkService {
    LinkInfo.Create create(LinkCommand.Create create);

    List<Long> deletes(LinkCommand.Delete command);

    List<Long> deleteAllByUserId(Long userId);

    List<Long> deleteAllByGemBoxId(Long gemBoxId);

    LinkInfo.Main update(LinkCommand.Update updateCommand);

    Page<LinkInfo.Search> findAll(LinkCommand.SearchLinks searchLinks, Pageable pageable);

    LinkInfo.Detail findById(Long id, Long userId);
}
