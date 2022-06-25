package com.linkgem.domain.gembox;

public interface GemBoxService {

    GemBoxInfo.Create create(GemBoxCommand.Create command);
}
