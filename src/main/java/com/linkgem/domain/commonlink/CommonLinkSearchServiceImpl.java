package com.linkgem.domain.commonlink;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommonLinkSearchServiceImpl implements CommonLinkSearchService {

    private final CommonLinkReader commonLinkReader;

    @Override
    public Page<CommonLinkInfo.Main> findAll(CommonLinkQuery.FindAll findAllQuery, Pageable pageable) {
        return commonLinkReader.findAll(findAllQuery, pageable);
    }
}
