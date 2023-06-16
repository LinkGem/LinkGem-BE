package com.linkgem.application.commonlink.service.delete;

import com.linkgem.presentation.commonlink.cmd.CommonLinkCommand;

public interface CommonLinkDeleteService {
    void delete(CommonLinkCommand.DeleteOne deleteOneCommand);
}
