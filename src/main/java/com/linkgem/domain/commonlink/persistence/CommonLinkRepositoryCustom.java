package com.linkgem.domain.commonlink.persistence;

import com.linkgem.domain.commonlink.dto.CommonLinkCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.commonlink.dto.CommonLinkInfo;

public interface CommonLinkRepositoryCustom {
    Page<CommonLinkInfo.Main> findAll(CommonLinkCommand.FindAll findAllQuery, Pageable pageable);
}
