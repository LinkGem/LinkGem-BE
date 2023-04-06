package com.linkgem.domain.gembox.service;

import java.util.List;
import java.util.stream.Collectors;

import com.linkgem.domain.gembox.domain.GemBox;
import com.linkgem.domain.gembox.dto.GemBoxCommand;
import com.linkgem.domain.gembox.dto.GemBoxInfo;
import com.linkgem.domain.gembox.persistence.GemBoxPersistence;
import com.linkgem.domain.link.service.LinkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.link.persistence.LinkPersistence;
import com.linkgem.domain.common.exception.BusinessException;
import com.linkgem.domain.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GemBoxServiceImpl implements GemBoxService {

    private final GemBoxPersistence gemBoxPersistence;
    private final LinkPersistence linkPersistence;
    private final LinkService linkService;

    @Transactional
    @Override
    public GemBoxInfo.Create create(GemBoxCommand.Create command) {

        GemBox initGemBox = command.toEntity();

        if (isFull(command.getUserId())) {
            throw new BusinessException(ErrorCode.GEMBOX_IS_FULL);
        }

        if (isExisted(GemBoxCommand.SearchDuplication.of(command.getName(), command.getUserId()))) {
            throw new BusinessException(ErrorCode.GEMBOX_ALREADY_EXISTED);
        }

        GemBox createdGemBox = gemBoxPersistence.create(initGemBox);
        System.out.println("createdGemBox = " + createdGemBox);
        addLinkToGemBox(command, createdGemBox);

        return GemBoxInfo.Create.of(createdGemBox);
    }

    private void addLinkToGemBox(GemBoxCommand.Create command, GemBox createdGemBox) {
        if (command.getLinkIds() == null || command.getLinkIds().isEmpty()) {
            return;
        }

        command
            .getLinkIds()
            .forEach(linkId -> linkPersistence.find(linkId, command.getUserId())
                .ifPresent(createdGemBox::addLink));
    }

    @Transactional
    @Override
    public void update(GemBoxCommand.Update command) {

        GemBoxCommand.SearchDuplication searchDuplication =
            GemBoxCommand.SearchDuplication.of(
                command.getId(),
                command.getName(),
                command.getUserId()
            );

        if (isExisted(searchDuplication)) {
            throw new BusinessException(ErrorCode.GEMBOX_ALREADY_EXISTED);
        }

        GemBox gemBox = gemBoxPersistence.find(command.getId(), command.getUserId())
            .orElseThrow(() -> new BusinessException(ErrorCode.GEMBOX_NOT_FOUND));

        gemBox.updateName(command.getName());
    }

    @Override
    public List<GemBoxInfo.Main> findAll(Long userId) {
        return gemBoxPersistence.findAll(userId)
            .stream()
            .map(GemBoxInfo.Main::of)
            .collect(Collectors.toList());
    }

    @Override
    public Page<GemBoxInfo.Search> search(Long userId, Pageable pageable) {
        return gemBoxPersistence.search(userId, pageable);
    }

    @Override
    public GemBoxInfo.Main find(GemBoxCommand.SearchDetail searchDetail) {
        return gemBoxPersistence.find(searchDetail.getId(), searchDetail.getUserId())
            .map(GemBoxInfo.Main::of)
            .orElseThrow(() -> new BusinessException(ErrorCode.GEMBOX_NOT_FOUND));
    }

    @Transactional
    @Override
    public void delete(GemBoxCommand.Delete command) {

        GemBox gemBox = gemBoxPersistence.find(command.getId(), command.getUserId())
            .orElseThrow(() -> new BusinessException(ErrorCode.GEMBOX_NOT_FOUND));

        linkService.deleteAllByGemBoxId(gemBox.getId());
        gemBoxPersistence.delete(gemBox);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        linkService.deleteAllByUserId(userId);
        gemBoxPersistence.deleteAllByUserId(userId);
    }

    @Transactional
    @Override
    public void putLinksToGembox(GemBoxCommand.PutLinksToGembox command) {

        final Long userId = command.getUserId();
        final Long gemboxId = command.getGemBoxId();

        GemBox gemBox = gemBoxPersistence.get(gemboxId, userId);

        command.getLinkIds()
            .stream()
            .map(linkId -> linkPersistence.get(linkId, userId))
            .forEach(link -> link.updateGemBox(gemBox));
    }

    @Override
    public boolean isExisted(GemBoxCommand.SearchDuplication searchDuplication) {
        return gemBoxPersistence.find(searchDuplication.getName(), searchDuplication.getUserId())
                .map(gemBox -> !gemBox.isEqual(searchDuplication.getId()))
                .orElse(false);
    }

    @Override
    public boolean isFull(Long userId) {
        return gemBoxPersistence.findAll(userId).size() >= GemBox.MAX_GEMBOX;
    }

}
