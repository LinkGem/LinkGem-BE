package com.linkgem.domain.link;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public LinkInfo.Create create(LinkCommand.Create create) {

        User user = userReader.find(create.getUserId())
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //todo : openGraph 조회 로직 추가
        OpenGraph openGraph = null;

        Link link = Link.builder()
            .memo(create.getMemo())
            .url(create.getUrl())
            .user(user)
            .openGraph(openGraph)
            .build();

        return LinkInfo.Create.of(linkStore.create(link));
    }
}
