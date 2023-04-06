package com.linkgem.domain.commonlink.service;

import com.linkgem.domain.commonlink.dto.CommonLinkCommand;
import com.linkgem.domain.commonlink.dto.CommonLinkInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonLinkService {

    CommonLinkInfo.Main create(CommonLinkCommand.Create createCommand);

    void delete(CommonLinkCommand.DeleteOne deleteOneCommand);

    Page<CommonLinkInfo.Main> findAll(CommonLinkCommand.FindAll findAllQuery, Pageable pageable);
}
