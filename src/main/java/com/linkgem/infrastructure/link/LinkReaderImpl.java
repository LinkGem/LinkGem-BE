package com.linkgem.infrastructure.link;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class LinkReaderImpl implements LinkReader {

    private final LinkRepository linkRepository;

    @Override
    public Page<LinkInfo.Search> findAll(Long userId, Pageable pageable) {
        return linkRepository.findAll(userId, pageable);
    }
}
