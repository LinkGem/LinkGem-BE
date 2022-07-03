package com.linkgem.domain.gembox;

import java.util.List;

public interface GemBoxService {

    GemBoxInfo.Create create(GemBoxCommand.Create command);

    void update(GemBoxCommand.Update command);

    List<GemBoxInfo.Main> findAll(Long userId);

    GemBoxInfo.Main find(GemBoxQuery.SearchDetail searchDetail);

    void delete(GemBoxCommand.Delete command);
}
