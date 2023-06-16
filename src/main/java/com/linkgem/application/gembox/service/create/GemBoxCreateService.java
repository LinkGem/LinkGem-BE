package com.linkgem.application.gembox.service.create;

import com.linkgem.presentation.gembox.cmd.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxInfo;

public interface GemBoxCreateService {
    GemBoxInfo.Create create(GemBoxCommand.Create command);
}
