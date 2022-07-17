package com.linkgem.domain.link;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LinkSearchServiceImpl implements LinkSearchService {
    private final LinkReader linkReader;

    @Override
    public Page<LinkInfo.Search> findAll(LinkQuery.SearchLinks searchLinks, Pageable pageable) {
        return linkReader.findAll(searchLinks, pageable);
    }

    @Override
    public LinkInfo.Detail findById(Long id, Long userId) {
        Link findLink = linkReader.get(id, userId);

        Long gemBoxId = null;
        String gemBoxName = null;

        if (findLink.getGemBox() != null) {
            gemBoxId = findLink.getGemBox().getId();
            gemBoxName = findLink.getGemBox().getName();
        }

        return LinkInfo.Detail.of(findLink, gemBoxId, gemBoxName);
    }
}
