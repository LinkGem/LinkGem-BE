package com.linkgem.gembox.application.service.create;

import com.linkgem.gembox.application.service.search.GemBoxDomainService;
import com.linkgem.gembox.infrastructure.GemBoxReader;
import com.linkgem.gembox.infrastructure.GemBoxStore;
import com.linkgem.gembox.presentation.cmd.GemBoxCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.application.link.service.delete.LinkDeleteService;
import com.linkgem.infrastructure.link.LinkReader;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GemBoxCreateServiceImpl implements GemBoxCreateService {

    private final GemBoxStore gemBoxStore;
    private final GemBoxReader gemBoxReader;

    private final LinkReader linkReader;
    private final LinkDeleteService linkDeleteService;

    private final GemBoxDomainService gemBoxDomainService;

    @Transactional
    @Override
    public GemBoxInfo.Create create(GemBoxCommand.Create command) {

        GemBox initGemBox = command.toEntity();

        if (gemBoxDomainService.isFull(command.getUserId())) {
            throw new BusinessException(ErrorCode.GEMBOX_IS_FULL);
        }

        if (gemBoxDomainService.isExisted(GemBoxQuery.SearchDuplication.of(command.getName(), command.getUserId()))) {
            throw new BusinessException(ErrorCode.GEMBOX_ALREADY_EXISTED);
        }

        GemBox createdGemBox = gemBoxStore.create(initGemBox);

        addLinkToGemBox(command, createdGemBox);

        return GemBoxInfo.Create.of(createdGemBox);
    }

    private void addLinkToGemBox(GemBoxCommand.Create command, GemBox createdGemBox) {
        if (command.getLinkIds() == null || command.getLinkIds().isEmpty()) {
            return;
        }

        command
                .getLinkIds()
                .forEach(linkId -> linkReader.find(linkId, command.getUserId())
                        .ifPresent(createdGemBox::addLink));
    }
}
