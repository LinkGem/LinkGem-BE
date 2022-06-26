package com.linkgem.domain.gembox;

import java.util.List;

public interface GemBoxService {

    GemBoxInfo.Create create(GemBoxCommand.Create command);

    List<GemBoxInfo.Main> findAll(Long userId);
}
