package com.linkgem.application.commonlink.service.search;

import com.linkgem.domain.commonlink.CommonLinkInfo;
import com.linkgem.domain.commonlink.CommonLinkQuery;
import com.linkgem.infrastructure.commonlink.CommonLinkReader;
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
