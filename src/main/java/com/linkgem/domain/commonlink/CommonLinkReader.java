package com.linkgem.domain.commonlink;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonLinkReader {

    Optional<CommonLink> findOne(CommonLinkQuery.FindOne findOneQuery);

    Page<CommonLinkInfo.Main> findAll(CommonLinkQuery.FindAll findAllQuery, Pageable pageable);
}
