package com.linkgem.infrastructure.commonlink;

import java.util.Optional;

import com.linkgem.domain.commonlink.CommonLink;
import com.linkgem.domain.commonlink.CommonLinkInfo;
import com.linkgem.domain.commonlink.CommonLinkQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonLinkReader {

    Optional<CommonLink> findOne(CommonLinkQuery.FindOne findOneQuery);

    Page<CommonLinkInfo.Main> findAll(CommonLinkQuery.FindAll findAllQuery, Pageable pageable);
}
