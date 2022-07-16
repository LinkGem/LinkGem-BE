package com.linkgem.domain.link;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LinkDeleteServiceImpl implements LinkDeleteService {

    private final LinkReader linkReader;
    private final LinkStore linkStore;

    @Transactional
    @Override
    public List<Long> deletes(LinkCommand.Delete command) {

        final Long userId = command.getUserId();

        return command.getIds()
            .stream()
            .map(id -> linkReader.find(id, userId))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(linkStore::delete)
            .collect(Collectors.toList())
            ;
    }
}
