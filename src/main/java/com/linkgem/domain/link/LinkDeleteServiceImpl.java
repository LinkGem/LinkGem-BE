package com.linkgem.domain.link;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.common.file.FileCommand;
import com.linkgem.domain.common.file.FileStore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LinkDeleteServiceImpl implements LinkDeleteService {

    private final LinkReader linkReader;
    private final LinkStore linkStore;
    private final FileStore fileStore;

    @Transactional
    @Override
    public List<Long> deletes(LinkCommand.Delete command) {

        final Long userId = command.getUserId();

        return command.getIds()
            .stream()
            .map(id -> linkReader.find(id, userId))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(this::deleteLink)
            .collect(Collectors.toList())
            ;
    }

    @Override
    public List<Long> deleteAllByUserId(Long userId) {
        return linkReader.findAllByUserId(userId)
            .stream()
            .map(this::deleteLink)
            .collect(Collectors.toList());

    }

    @Override
    public List<Long> deleteAllByGemBoxId(Long gemBoxId) {
        return linkReader.findAllByGemBoxId(gemBoxId)
            .stream()
            .map(this::deleteLink)
            .collect(Collectors.toList());
    }

    private Long deleteLink(Link link) {

        if (link.hasImageUrl()) {
            fileStore.delete(new FileCommand.DeleteFile(link.getOpenGraph().getImageUrl()));
        }
        return linkStore.delete(link);
    }
}
