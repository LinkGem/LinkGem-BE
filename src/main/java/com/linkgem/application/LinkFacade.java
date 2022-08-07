package com.linkgem.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.linkgem.domain.link.LinkCommand;
import com.linkgem.domain.link.LinkCreateService;
import com.linkgem.domain.link.LinkDeleteService;
import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkQuery;
import com.linkgem.domain.link.LinkSearchService;
import com.linkgem.domain.link.LinkUpdateService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LinkFacade {

    private final LinkCreateService linkCreateService;
    private final LinkSearchService linkSearchService;
    private final LinkDeleteService linkDeleteService;
    private final LinkUpdateService linkUpdateService;

    public LinkInfo.Create create(LinkCommand.Create create) {
        return linkCreateService.create(create);
    }

    public Page<LinkInfo.Search> findAll(LinkQuery.SearchLinks searchLinks, Pageable pageable) {
        return linkSearchService.findAll(searchLinks, pageable);
    }

    public LinkInfo.Detail findById(Long id, Long userId) {
        return linkSearchService.findById(id, userId);
    }

    public List<Long> deletes(LinkCommand.Delete deleteCommand) {
        return linkDeleteService.deletes(deleteCommand);
    }

    public LinkInfo.Main update(LinkCommand.Update updateCommand) {
        return linkUpdateService.update(updateCommand);
    }
}
