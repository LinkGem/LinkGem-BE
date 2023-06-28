package com.linkgem.domain.gembox;

import com.linkgem.domain.link.Link;
import com.linkgem.domain.link.LinkDeleteService;
import com.linkgem.domain.link.LinkReader;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GemBoxServiceImpl implements GemBoxService {

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

    @Transactional
    @Override
    public void merge(GemBoxCommand.Merge command) {

        GemBox targetGemBox = gemBoxReader.find(command.getTargetId(), command.getUserId())
            .orElseThrow(() -> new BusinessException(ErrorCode.GEMBOX_NOT_FOUND));

        GemBox sourceGemBox = gemBoxReader.find(command.getSourceId(), command.getUserId())
            .orElseThrow(() -> new BusinessException(ErrorCode.GEMBOX_NOT_FOUND));

        linkReader.findAllByGemBoxId(command.getTargetId())
            .stream()
            .forEach(link -> link.updateGemBox(sourceGemBox));

        gemBoxStore.delete(targetGemBox);
    }

    @Transactional
    @Override
    public GemBoxInfo.MergeMulti mergeMulti(GemBoxCommand.MergeMulti command) {

        // 새로운 잼박스 생성
        GemBoxInfo.Create newGembox = this.create(GemBoxCommand.Create.createMergeGembox(command.getName(), command.getUserId()));
        GemBox gemBox = gemBoxReader.get(newGembox.getId(), command.getUserId());

        // 선택된 잼박스의 링크 이동
        if(command.getGemboxIds().isEmpty()) {
            throw new BusinessException(ErrorCode.GEMBOX_NOT_CHOOSE);
        }

        List<Link> linkList = command.getGemboxIds()
            .stream()
            .flatMap(gemBoxId -> linkReader.findAllByGemBoxId(gemBoxId).stream())
            .collect(Collectors.toList());

        linkList.stream()
            .forEach(link -> link.updateGemBox(gemBox));

        // 선택된 잼박스 삭제
        List<GemBox> mergedGembox = command.getGemboxIds()
            .stream()
            .map(gemBoxId -> gemBoxReader.get(gemBoxId, command.getUserId()))
            .collect(Collectors.toList());

        mergedGembox.stream().forEach(deleteGemBox -> gemBoxStore.delete(deleteGemBox));

        GemBox finalGembox = gemBoxReader.get(newGembox.getId(), command.getUserId());

        return GemBoxInfo.MergeMulti.of(finalGembox);
    }

}
