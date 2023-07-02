package com.linkgem.domain.link;

import java.util.List;

public interface LinkStore {

    Link create(Link link);

    List<Link> createAll(List<Link> links);

    Long delete(Link link);

    void deleteAllByUserId(Long userId);
}
