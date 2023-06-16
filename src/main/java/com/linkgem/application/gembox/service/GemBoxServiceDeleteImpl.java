package com.linkgem.application.gembox.service;

import com.linkgem.application.gembox.service.search.GemBoxDomainService;
import com.linkgem.application.link.service.delete.LinkDeleteService;
import com.linkgem.domain.gembox.*;
import com.linkgem.infrastructure.link.LinkReader;
import com.linkgem.infrastructure.gembox.GemBoxReader;
import com.linkgem.infrastructure.gembox.GemBoxStore;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import com.linkgem.presentation.gembox.cmd.GemBoxCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GemBoxServiceDeleteImpl implements GemBoxService {

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

    @Transactional
    @Override
    public void update(GemBoxCommand.Update command) {

        GemBoxQuery.SearchDuplication searchDuplication =
            GemBoxQuery.SearchDuplication.of(
                command.getId(),
                command.getName(),
                command.getUserId()
            );

        if (gemBoxDomainService.isExisted(searchDuplication)) {
            throw new BusinessException(ErrorCode.GEMBOX_ALREADY_EXISTED);
        }

        GemBox gemBox = gemBoxReader.find(command.getId(), command.getUserId())
            .orElseThrow(() -> new BusinessException(ErrorCode.GEMBOX_NOT_FOUND));

        gemBox.updateName(command.getName());
    }

    @Override
    public List<GemBoxInfo.Main> findAll(Long userId) {
        return gemBoxReader.findAll(userId)
            .stream()
            .map(GemBoxInfo.Main::of)
            .collect(Collectors.toList());
    }

    @Override
    public Page<GemBoxInfo.Search> search(Long userId, Pageable pageable) {
        return gemBoxReader.search(userId, pageable);
    }

    @Override
    public GemBoxInfo.Main find(GemBoxQuery.SearchDetail searchDetail) {
        return gemBoxReader.find(searchDetail.getId(), searchDetail.getUserId())
            .map(GemBoxInfo.Main::of)
            .orElseThrow(() -> new BusinessException(ErrorCode.GEMBOX_NOT_FOUND));
    }

    @Transactional
    @Override
    public void delete(GemBoxCommand.Delete command) {

        GemBox gemBox = gemBoxReader.find(command.getId(), command.getUserId())
            .orElseThrow(() -> new BusinessException(ErrorCode.GEMBOX_NOT_FOUND));

        linkDeleteService.deleteAllByGemBoxId(gemBox.getId());
        gemBoxStore.delete(gemBox);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        linkDeleteService.deleteAllByUserId(userId);
        gemBoxStore.deleteAllByUserId(userId);
    }

    @Transactional
    @Override
    public void putLinksToGembox(GemBoxCommand.PutLinksToGembox command) {

        final Long userId = command.getUserId();
        final Long gemboxId = command.getGemBoxId();

        GemBox gemBox = gemBoxReader.get(gemboxId, userId);

        command.getLinkIds()
            .stream()
            .map(linkId -> linkReader.get(linkId, userId))
            .forEach(link -> link.updateGemBox(gemBox));
    }

}
