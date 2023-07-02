package com.linkgem.domain.gembox;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.linkgem.domain.link.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GemBoxServiceImpl implements GemBoxService {

    private final GemBoxStore gemBoxStore;
    private final GemBoxReader gemBoxReader;

    private final LinkReader linkReader;

    private final GemBoxDomainService gemBoxDomainService;

    private final LinkCreateService linkCreateService;
    private final LinkDeleteService linkDeleteService;



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
    public GemBoxInfo.Create copyGembox(GemBoxQuery.SearchDetail searchDetail){
        GemBox originGembox = gemBoxReader.find(searchDetail.getId(), searchDetail.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.GEMBOX_NOT_FOUND));

        GemBoxCommand.Create command = GemBoxCommand
                .Create.builder()
                .name(generateCopiedGemBoxName(originGembox.getName()))
                .userId(originGembox.getUserId()).build();

        GemBoxInfo.Create copiedGemboxInfo = this.create(command);
        GemBox copiedGembox = gemBoxReader.find(copiedGemboxInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.GEMBOX_NOT_FOUND));

        List<Link> copiedLinks = linkCreateService.copyLinks(originGembox.getLinks(), copiedGembox);

        return new GemBoxInfo.Create(
                copiedGembox.getId(),
                copiedGembox.getName(),
                copiedGembox.getUserId(),
                copiedLinks.stream().map(Link::getId)
                        .collect(Collectors.toList())
        );
    }

    private String generateCopiedGemBoxName(String name) {
        String initSuffix = "(1)";
        int length = name.length();

        if (length <= 3) {
            return name + initSuffix;
        }

        // (n) 패턴 값 추출 한 후 (n+1) 변경하여 반환
        String pattern = "\\((\\d+)\\)$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(name);

        StringBuffer sb = new StringBuffer();
        boolean isMatched = false;
        while (matcher.find()) {
            int lastSuffixNumber = Integer.parseInt(matcher.group(1));
            int nextSuffixNumber = lastSuffixNumber + 1;
            matcher.appendReplacement(sb, "(" + nextSuffixNumber + ")");
            isMatched = true;
        }
        matcher.appendTail(sb);

        if (!isMatched) {
            sb.append(initSuffix);
        }

        return sb.toString();
    }

}
