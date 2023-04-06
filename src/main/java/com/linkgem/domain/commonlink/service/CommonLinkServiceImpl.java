package com.linkgem.domain.commonlink.service;

import com.linkgem.domain.commonlink.domain.CommonLink;
import com.linkgem.domain.commonlink.dto.CommonLinkCommand;
import com.linkgem.domain.commonlink.dto.CommonLinkInfo;
import com.linkgem.domain.commonlink.persistence.CommonLinkPersistence;
import com.linkgem.domain.link.opengraph.OpenGraph;
import com.linkgem.domain.link.opengraph.OpenGraphReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class CommonLinkServiceImpl implements CommonLinkService {

    private final CommonLinkPersistence commonLinkPersistence;

    private final OpenGraphReader openGraphReader;

    @Override
    public CommonLinkInfo.Main create(CommonLinkCommand.Create createCommand) {

        OpenGraph openGraph = openGraphReader.call(createCommand.getUrl());

        CommonLink commonLink = createCommand.toEntity(openGraph);

        return CommonLinkInfo.Main.of(commonLinkPersistence.create(commonLink));
    }

    @Override
    public void delete(CommonLinkCommand.DeleteOne deleteOneCommand) {
        CommonLinkCommand.FindOne findOneQuery = new CommonLinkCommand.FindOne(deleteOneCommand.getId());

        CommonLink commonLink = commonLinkPersistence.findOne(findOneQuery)
                .orElseThrow(EntityNotFoundException::new);

        commonLinkPersistence.delete(commonLink);
    }
    
    @Override
    public Page<CommonLinkInfo.Main> findAll(CommonLinkCommand.FindAll findAllQuery, Pageable pageable) {
        return commonLinkPersistence.findAll(findAllQuery, pageable);
    }
}
