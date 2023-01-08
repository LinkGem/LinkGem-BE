package com.linkgem.domain.commonlink;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonLinkSearchService {

    Page<CommonLinkInfo.Main> findAll(CommonLinkQuery.FindAll findAllQuery, Pageable pageable);
}
