package com.linkgem.application;

import org.springframework.stereotype.Component;

import com.linkgem.domain.gembox.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxInfo;
import com.linkgem.domain.gembox.GemBoxService;

@Component
public record GemBoxFacade(GemBoxService gemBoxService) {

    public GemBoxInfo.Create create(GemBoxCommand.Create command) {
        return gemBoxService.create(command);
    }
}
