package com.linkgem.application.commonlink.service.search;

import com.linkgem.domain.commonlink.CommonLinkInfo;
import com.linkgem.domain.commonlink.CommonLinkQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonLinkSearchService {

    Page<CommonLinkInfo.Main> findAll(CommonLinkQuery.FindAll findAllQuery, Pageable pageable);
}
