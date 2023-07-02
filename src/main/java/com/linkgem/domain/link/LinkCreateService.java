package com.linkgem.domain.link;

import com.linkgem.domain.gembox.GemBox;

import java.util.List;

public interface LinkCreateService {

    LinkInfo.Create create(LinkCommand.Create create);

    List<Link> copyLinks(List<Link> links, GemBox gemBox);
}
