package com.linkgem.domain.commonlink;

import javax.persistence.EntityNotFoundException;

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
