package com.linkgem.application.link.service.create;

import com.linkgem.presentation.link.cmd.LinkCommand;
import com.linkgem.domain.link.LinkInfo;

public interface LinkCreateService {

    LinkInfo.Create create(LinkCommand.Create create);
}
