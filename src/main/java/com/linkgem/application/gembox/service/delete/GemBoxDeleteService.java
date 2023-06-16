package com.linkgem.application.gembox.service.delete;

import com.linkgem.presentation.gembox.cmd.GemBoxCommand;

public interface GemBoxDeleteService {
    void delete(GemBoxCommand.Delete command);

    void deleteAllByUserId(Long userId);
}
