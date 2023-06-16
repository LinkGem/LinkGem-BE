package com.linkgem.gembox.application.facade;

import com.linkgem.gembox.application.service.GemBoxService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.linkgem.gembox.presentation.cmd.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxInfo;
import com.linkgem.domain.gembox.GemBoxQuery;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GemBoxFacade {

    private final GemBoxService gemBoxService;

    public GemBoxInfo.Create create(GemBoxCommand.Create command) {
        return gemBoxService.create(command);
    }

    public void update(GemBoxCommand.Update command) {
        gemBoxService.update(command);
    }

    public void delete(GemBoxCommand.Delete command) {
        gemBoxService.delete(command);
    }

    public Page<GemBoxInfo.Search> search(Long userId, Pageable pageable) {
        return gemBoxService.search(userId, pageable);
    }

    public GemBoxInfo.Main find(GemBoxQuery.SearchDetail searchDetail) {
        return gemBoxService.find(searchDetail);
    }

    public void putLinksToGembox(GemBoxCommand.PutLinksToGembox command) {
        gemBoxService.putLinksToGembox(command);
    }
}
