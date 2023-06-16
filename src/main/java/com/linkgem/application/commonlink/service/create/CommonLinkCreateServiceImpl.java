package com.linkgem.application.commonlink.service.create;

import com.linkgem.domain.commonlink.CommonLink;
import com.linkgem.presentation.commonlink.cmd.CommonLinkCommand;
import com.linkgem.domain.commonlink.CommonLinkInfo;
import com.linkgem.infrastructure.commonlink.CommonLinkStore;
import org.springframework.stereotype.Service;

import com.linkgem.domain.link.opengraph.OpenGraph;
import com.linkgem.infrastructure.link.opengraph.OpenGraphReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommonLinkCreateServiceImpl implements CommonLinkCreateService {

    private final CommonLinkStore commonLinkStore;
    private final OpenGraphReader openGraphReader;

    @Override
    public CommonLinkInfo.Main create(CommonLinkCommand.Create createCommand) {

        OpenGraph openGraph = openGraphReader.call(createCommand.getUrl());

        CommonLink commonLink = createCommand.toEntity(openGraph);

        return CommonLinkInfo.Main.of(commonLinkStore.create(commonLink));
    }
}
