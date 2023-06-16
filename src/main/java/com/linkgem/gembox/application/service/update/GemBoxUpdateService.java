package com.linkgem.gembox.application.service.update;

import com.linkgem.gembox.presentation.cmd.GemBoxCommand;

public interface GemBoxUpdateService {

    void update(GemBoxCommand.Update command);
}
