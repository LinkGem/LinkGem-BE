package com.linkgem.domain.link.opengraph;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.linkgem.domain.gembox.GemBox;
import com.linkgem.domain.gembox.GemBoxReader;
import com.linkgem.domain.link.Link;
import com.linkgem.domain.link.LinkCommand;
import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LinkUpdateServiceImpl implements LinkUpdateService {

    private final LinkReader linkReader;
    private final GemBoxReader gemBoxReader;

    @Transactional
    @Override
    public LinkInfo.Main update(LinkCommand.Update updateCommand) {

        final Long userId = updateCommand.getUserId();
        Link findLink = linkReader.get(updateCommand.getId(), userId);

        if (StringUtils.hasText(updateCommand.getMemo())) {
            findLink.updateMemo(updateCommand.getMemo());
        }

        if (Objects.nonNull(updateCommand.getGemBoxId())) {
            this.updateGemBox(findLink, updateCommand.getGemBoxId(), userId);
        }

        return LinkInfo.Main.of(findLink);
    }

    private void updateGemBox(Link link, Long gemBoxId, Long userId) {
        GemBox gemBox = gemBoxReader.get(gemBoxId, userId);
        link.updateGemBox(gemBox);
    }
}
