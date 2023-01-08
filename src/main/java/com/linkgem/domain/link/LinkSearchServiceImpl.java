package com.linkgem.domain.link;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

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
        Link link = linkReader.findOneJoinUser(id, userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.LINK_NOT_FOUND));

        return LinkInfo.Detail.of(link);
    }
}
