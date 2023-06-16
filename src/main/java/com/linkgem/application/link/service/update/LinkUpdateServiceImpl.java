package com.linkgem.application.link.service.update;

import java.util.Objects;

import com.linkgem.domain.link.Link;
import com.linkgem.presentation.link.cmd.LinkCommand;
import com.linkgem.domain.link.LinkInfo;
import com.linkgem.infrastructure.link.LinkReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.gembox.GemBox;
import com.linkgem.infrastructure.gembox.GemBoxReader;

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

        if (updateCommand.getMemo() != null) {
            findLink.updateMemo(updateCommand.getMemo());
        }

        if (Objects.nonNull(updateCommand.getGemBoxId())) {
            this.updateGemBox(findLink, updateCommand.getGemBoxId(), userId);
        }

        if (Objects.nonNull(updateCommand.getIsFavorites())) {
            findLink.updateFavorites(updateCommand.getIsFavorites());
        }

        return LinkInfo.Main.of(findLink);
    }

    private void updateGemBox(Link link, Long gemBoxId, Long userId) {
        GemBox gemBox = gemBoxReader.get(gemBoxId, userId);
        link.updateGemBox(gemBox);
    }
}
