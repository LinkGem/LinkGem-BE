package com.linkgem.application.link.service.update;

import com.linkgem.presentation.link.cmd.LinkCommand;
import com.linkgem.domain.link.LinkInfo;

public interface LinkUpdateService {
    LinkInfo.Main update(LinkCommand.Update updateCommand);
}
