package com.linkgem.domain.commonlink.persistence;

import java.util.Optional;

import com.linkgem.domain.commonlink.dto.CommonLinkCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.linkgem.domain.commonlink.domain.CommonLink;
import com.linkgem.domain.commonlink.dto.CommonLinkInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CommonLinkPersistenceImpl implements CommonLinkPersistence {

    private final CommonLinkRepository commonLinkRepository;

    @Override
    public Optional<CommonLink> findOne(CommonLinkCommand.FindOne findOneQuery) {
        return commonLinkRepository.findById(findOneQuery.getId());
    }

    @Override
    public Page<CommonLinkInfo.Main> findAll(CommonLinkCommand.FindAll findAllQuery, Pageable pageable) {
        return commonLinkRepository.findAll(findAllQuery, pageable);
    }

    @Override
    public void delete(CommonLink commonLink) {
        commonLinkRepository.delete(commonLink);
    }

    @Override
    public CommonLink create(CommonLink commonLink) {
        return commonLinkRepository.save(commonLink);
    }
}
