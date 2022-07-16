package com.linkgem.domain.link.opengraph;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.link.Link;
import com.linkgem.domain.link.LinkCommand;
import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LinkUpdateServiceImpl implements LinkUpdateService {

    private final LinkReader linkReader;

    @Transactional
    @Override
    public LinkInfo.Main update(LinkCommand.Update updateCommand) {
        Link findLink = linkReader.get(updateCommand.getId(), updateCommand.getUserId());

        findLink.updateMemo(updateCommand.getMemo());

        return LinkInfo.Main.of(findLink);
    }
}
