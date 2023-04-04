package com.linkgem.infrastructure.commonlink;

import com.linkgem.domain.commonlink.CommonLinkCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.commonlink.CommonLinkInfo;

public interface CommonLinkRepositoryCustom {
    Page<CommonLinkInfo.Main> findAll(CommonLinkCommand.FindAll findAllQuery, Pageable pageable);
}
