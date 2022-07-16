package com.linkgem.domain.link.opengraph;

import com.linkgem.domain.link.LinkCommand;
import com.linkgem.domain.link.LinkInfo;

public interface LinkUpdateService {
    LinkInfo.Main update(LinkCommand.Update updateCommand);
}
