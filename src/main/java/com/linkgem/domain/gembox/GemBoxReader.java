package com.linkgem.domain.gembox;

import java.util.List;
import java.util.Optional;

public interface GemBoxReader {

    Optional<GemBox> find(String name, Long userId);

    Optional<GemBox> find(Long id);

    List<GemBox> findAll(Long userId);
}
