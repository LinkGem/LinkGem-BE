package com.linkgem.application.gembox.service.update;

import com.linkgem.presentation.gembox.cmd.GemBoxCommand;

public interface GemBoxUpdateService {

    void update(GemBoxCommand.Update command);
}
