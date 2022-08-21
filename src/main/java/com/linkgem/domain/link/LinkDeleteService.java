package com.linkgem.domain.link;

import java.util.List;

public interface LinkDeleteService {

    List<Long> deletes(LinkCommand.Delete command);

    List<Long> deleteAllByUserId(Long userId);

    List<Long> deleteAllByGemBoxId(Long gemBoxId);
}
