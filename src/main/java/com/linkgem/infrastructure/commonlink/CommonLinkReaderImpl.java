package com.linkgem.infrastructure.commonlink;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.linkgem.domain.commonlink.CommonLink;
import com.linkgem.domain.commonlink.CommonLinkInfo;
import com.linkgem.domain.commonlink.CommonLinkQuery;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CommonLinkReaderImpl implements CommonLinkReader {

    private final CommonLinkRepository commonLinkRepository;
    private final CommonLinkQueryDslRepository commonLinkQueryDslRepository;

    @Override
    public Optional<CommonLink> findOne(CommonLinkQuery.FindOne findOneQuery) {
        return commonLinkRepository.findById(findOneQuery.getId());
    }

    @Override
    public Page<CommonLinkInfo.Main> findAll(CommonLinkQuery.FindAll findAllQuery, Pageable pageable) {
        return commonLinkQueryDslRepository.findAll(findAllQuery, pageable);
    }
}
