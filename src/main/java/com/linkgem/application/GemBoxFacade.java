package com.linkgem.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.linkgem.domain.gembox.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxInfo;
import com.linkgem.domain.gembox.GemBoxService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GemBoxFacade {

    private final GemBoxService gemBoxService;

    public GemBoxInfo.Create create(GemBoxCommand.Create command) {
        return gemBoxService.create(command);
    }

    public List<GemBoxInfo.Main> findAll(Long userId) {
        return gemBoxService.findAll(userId);
    }
}
