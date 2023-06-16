package com.linkgem.gembox.application.service.create;

import com.linkgem.gembox.presentation.cmd.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxInfo;

public interface GemBoxCreateService {
    GemBoxInfo.Create create(GemBoxCommand.Create command);
}
