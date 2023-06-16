package com.linkgem.infrastructure.commonlink;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.commonlink.CommonLink;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CommonLinkStoreImpl implements CommonLinkStore {

    private final CommonLinkRepository commonLinkRepository;

    @Override
    public void delete(CommonLink commonLink) {
        commonLinkRepository.delete(commonLink);
    }

    @Override
    public CommonLink create(CommonLink commonLink) {
        return commonLinkRepository.save(commonLink);
    }
}
