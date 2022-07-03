package com.linkgem.application;

import org.springframework.stereotype.Component;

import com.linkgem.domain.link.LinkCommand;
import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkCreateService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LinkFacade {

    private final LinkCreateService linkCreateService;

    public LinkInfo.Create create(LinkCommand.Create create) {
        return linkCreateService.create(create);
    }
}
