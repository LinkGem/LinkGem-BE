package com.linkgem.domain.gembox;

import org.springframework.stereotype.Service;

import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GemBoxServiceImpl implements GemBoxService {

    private final GemBoxStore gemBoxStore;
    private final GemBoxReader gemBoxReader;

    private final GemBoxDomainService gemBoxDomainService;

    @Override
    public GemBoxInfo.Create create(GemBoxCommand.Create command) {

        GemBox initGemBox = command.toEntity();

        if (gemBoxDomainService.isFull(command.getUserId())) {
            throw new BusinessException(ErrorCode.GEMBOX_IS_FULL);
        }

        if (gemBoxDomainService.isExisted(initGemBox)) {
            throw new BusinessException(ErrorCode.GEMBOX_IS_ALREADY_EXISTED);
        }

        // command.getLinkIds();
        //TODO : 링크 리스트의 잼박스 ID를 업데이트한다
        return GemBoxInfo.Create.of(gemBoxStore.create(initGemBox));
    }

}
