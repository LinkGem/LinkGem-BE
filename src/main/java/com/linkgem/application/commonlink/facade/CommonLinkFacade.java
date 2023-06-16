package com.linkgem.application.commonlink.facade;

import com.linkgem.application.commonlink.service.create.CommonLinkCreateService;
import com.linkgem.application.commonlink.service.delete.CommonLinkDeleteService;
import com.linkgem.application.commonlink.service.search.CommonLinkSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.linkgem.presentation.commonlink.cmd.CommonLinkCommand;
import com.linkgem.domain.commonlink.CommonLinkInfo;
import com.linkgem.domain.commonlink.CommonLinkQuery;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CommonLinkFacade {

    private final CommonLinkCreateService commonLinkCreateService;
    private final CommonLinkDeleteService commonLinkDeleteService;
    private final CommonLinkSearchService commonLinkSearchService;

    public CommonLinkInfo.Main create(CommonLinkCommand.Create createCommand) {
        return commonLinkCreateService.create(createCommand);
    }

    public void delete(CommonLinkCommand.DeleteOne deleteOneCommand) {
        commonLinkDeleteService.delete(deleteOneCommand);
    }

    public Page<CommonLinkInfo.Main> findAll(CommonLinkQuery.FindAll findAllQuery, Pageable pageable) {
        return commonLinkSearchService.findAll(findAllQuery, pageable);
    }
}
