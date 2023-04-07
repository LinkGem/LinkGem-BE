package com.linkgem.domain.gem.persistence;

import com.linkgem.domain.gem.dto.CommonLinkCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.linkgem.domain.gem.dto.CommonLinkInfo;

public interface CommonLinkRepositoryCustom {
    Page<CommonLinkInfo.Main> findAll(CommonLinkCommand.FindAll findAllQuery, Pageable pageable);
}
