package com.linkgem.application.commonlink.service.create;

import com.linkgem.presentation.commonlink.cmd.CommonLinkCommand;
import com.linkgem.domain.commonlink.CommonLinkInfo;

public interface CommonLinkCreateService {

    CommonLinkInfo.Main create(CommonLinkCommand.Create createCommand);
}
