package com.linkgem.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.linkgem.domain.gembox.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxInfo;
import com.linkgem.domain.gembox.GemBoxQuery;
import com.linkgem.domain.gembox.GemBoxService;

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

    public List<GemBoxInfo.Main> findAll(Long userId) {
        return gemBoxService.findAll(userId);
    }

    public GemBoxInfo.Main find(GemBoxQuery.SearchDetail searchDetail) {
        return gemBoxService.find(searchDetail);
    }
}
