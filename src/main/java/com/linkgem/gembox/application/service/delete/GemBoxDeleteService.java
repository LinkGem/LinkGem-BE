package com.linkgem.gembox.application.service.delete;

import com.linkgem.gembox.presentation.cmd.GemBoxCommand;

public interface GemBoxDeleteService {
    void delete(GemBoxCommand.Delete command);

    void deleteAllByUserId(Long userId);
}
