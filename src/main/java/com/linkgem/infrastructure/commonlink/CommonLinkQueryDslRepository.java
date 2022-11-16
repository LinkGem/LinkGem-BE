package com.linkgem.infrastructure.commonlink;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.commonlink.CommonLinkInfo;
import com.linkgem.domain.commonlink.CommonLinkQuery;

public interface CommonLinkQueryDslRepository {
    Page<CommonLinkInfo.Main> findAll(CommonLinkQuery.FindAll findAllQuery, Pageable pageable);
}
