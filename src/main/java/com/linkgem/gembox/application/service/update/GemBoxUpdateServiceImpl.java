package com.linkgem.gembox.application.service.update;

import com.linkgem.gembox.application.service.search.GemBoxDomainService;
import com.linkgem.gembox.infrastructure.GemBoxReader;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import com.linkgem.gembox.presentation.cmd.GemBoxCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GemBoxUpdateServiceImpl implements GemBoxUpdateService {
    private final GemBoxReader gemBoxReader;
    private final GemBoxDomainService gemBoxDomainService;

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
}
