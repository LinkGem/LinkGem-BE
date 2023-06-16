package com.linkgem.application.gembox.service.delete;

import com.linkgem.domain.gembox.*;
import com.linkgem.infrastructure.gembox.GemBoxReader;
import com.linkgem.infrastructure.gembox.GemBoxStore;
import com.linkgem.presentation.gembox.cmd.GemBoxCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.application.link.service.delete.LinkDeleteService;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GemBoxDeleteServiceImpl implements GemBoxDeleteService {

    private final GemBoxStore gemBoxStore;
    private final GemBoxReader gemBoxReader;

    private final LinkDeleteService linkDeleteService;


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
}
