package com.linkgem.domain.link;

import com.linkgem.domain.link.LinkCommand;
import com.linkgem.domain.link.LinkInfo;

public interface LinkUpdateService {
    LinkInfo.Main update(LinkCommand.Update updateCommand);
}
