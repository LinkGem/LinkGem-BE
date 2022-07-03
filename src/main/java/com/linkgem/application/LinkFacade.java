package com.linkgem.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.linkgem.domain.link.LinkCommand;
import com.linkgem.domain.link.LinkCreateService;
import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkSearchService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LinkFacade {

    private final LinkCreateService linkCreateService;
    private final LinkSearchService linkSearchService;

    public LinkInfo.Create create(LinkCommand.Create create) {
        return linkCreateService.create(create);
    }

    public Page<LinkInfo.Search> findAll(Long userId, Pageable pageable) {
        return linkSearchService.findAll(userId, pageable);
    }
}
