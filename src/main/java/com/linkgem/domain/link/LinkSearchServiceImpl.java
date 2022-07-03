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
    public Page<LinkInfo.Search> findAll(Long userId, Pageable pageable) {
        return linkReader.findAll(userId, pageable);
    }
}
