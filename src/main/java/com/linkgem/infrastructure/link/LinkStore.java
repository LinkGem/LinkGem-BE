package com.linkgem.infrastructure.link;

import com.linkgem.domain.link.Link;

public interface LinkStore {

    Link create(Link link);

    Long delete(Link link);

    void deleteAllByUserId(Long userId);
}
