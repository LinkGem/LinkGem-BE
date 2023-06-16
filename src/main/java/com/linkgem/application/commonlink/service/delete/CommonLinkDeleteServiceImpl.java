package com.linkgem.application.commonlink.service.delete;

import javax.persistence.EntityNotFoundException;

import com.linkgem.domain.commonlink.*;
import com.linkgem.infrastructure.commonlink.CommonLinkReader;
import com.linkgem.infrastructure.commonlink.CommonLinkStore;
import com.linkgem.presentation.commonlink.cmd.CommonLinkCommand;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommonLinkDeleteServiceImpl implements CommonLinkDeleteService {

    private final CommonLinkReader commonLinkReader;
    private final CommonLinkStore commonLinkStore;

    @Override
    public void delete(CommonLinkCommand.DeleteOne deleteOneCommand) {
        CommonLinkQuery.FindOne findOneQuery = new CommonLinkQuery.FindOne(deleteOneCommand.getId());

        CommonLink commonLink = commonLinkReader.findOne(findOneQuery)
            .orElseThrow(EntityNotFoundException::new);

        commonLinkStore.delete(commonLink);
    }
}
