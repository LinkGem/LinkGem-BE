package com.linkgem.infrastructure.link;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.linkgem.domain.link.Link;
import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkQuery;
import com.linkgem.domain.link.LinkReader;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class LinkReaderImpl implements LinkReader {

    private final LinkRepository linkRepository;

    @Override
    public Page<LinkInfo.Search> findAll(LinkQuery.SearchLinks searchLinks, Pageable pageable) {
        return linkRepository.findAll(searchLinks, pageable);
    }

    @Override
    public List<Link> findAllByUserId(Long userId) {
        return linkRepository.findAllByUserId(userId);
    }

    @Override
    public List<Link> findAllByGemBoxId(Long gemBoxId) {
        return linkRepository.findAllByGemBoxId(gemBoxId);
    }

    @Override
    public Optional<Link> find(Long id, Long userId) {
        return linkRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public Link get(Long id, Long userId) {
        return this.find(id, userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.LINK_NOT_FOUND));
    }

    @Override
    public Optional<Link> findOneJoinUser(Long id, Long userId) {
        return linkRepository.findOneJoinUser(id, userId);
    }
}
