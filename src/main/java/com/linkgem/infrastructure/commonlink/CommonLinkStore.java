package com.linkgem.infrastructure.commonlink;

import com.linkgem.domain.commonlink.CommonLink;

public interface CommonLinkStore {

    void delete(CommonLink commonLink);

    CommonLink create(CommonLink commonLink);
}
