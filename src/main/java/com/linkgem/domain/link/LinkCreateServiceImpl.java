package com.linkgem.domain.link;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.link.opengraph.OpenGraph;
import com.linkgem.domain.link.opengraph.OpenGraphReader;
import com.linkgem.domain.user.User;
import com.linkgem.domain.user.UserReader;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LinkCreateServiceImpl implements LinkCreateService {

    private final LinkStore linkStore;

    private final UserReader userReader;

    private final OpenGraphReader openGraphReader;

    @Transactional
    @Override
    public LinkInfo.Create create(LinkCommand.Create create) {

        User user = userReader.find(create.getUserId())
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        OpenGraph openGraph = openGraphReader.call(create.getUrl());

        Link link = Link.builder()
            .memo(create.getMemo())
            .url(create.getUrl())
            .user(user)
            .openGraph(openGraph)
            .build();

        return LinkInfo.Create.of(linkStore.create(link));
    }
}
