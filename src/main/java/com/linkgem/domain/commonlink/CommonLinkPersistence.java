package com.linkgem.domain.commonlink;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonLinkPersistence {

    Optional<CommonLink> findOne(CommonLinkCommand.FindOne findOneQuery);

    Page<CommonLinkInfo.Main> findAll(CommonLinkCommand.FindAll findAllQuery, Pageable pageable);

    void delete(CommonLink commonLink);

    CommonLink create(CommonLink commonLink);
}
