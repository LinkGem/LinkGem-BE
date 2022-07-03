package com.linkgem.domain.gembox;

import java.util.List;
import java.util.stream.Collectors;

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

        // command.getLinkIds();
        //TODO : 링크 리스트의 잼박스 ID를 업데이트한다
        return GemBoxInfo.Create.of(gemBoxStore.create(initGemBox));
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

        //TODO : 링크삭제 로직을 추가해야한다
        gemBoxStore.delete(gemBox);
    }

}
