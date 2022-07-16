package com.linkgem.domain.link;

import java.util.List;

public interface LinkDeleteService {

    List<Long> deletes(LinkCommand.Delete command);
}
