package com.linkgem.domain.gembox;

import java.util.List;
import java.util.Optional;

public interface GemBoxReader {

    Optional<GemBox> find(String name, Long userId);

    Optional<GemBox> find(Long id);

    Optional<GemBox> find(Long id, Long userId);

    GemBox get(Long id, Long userId);

    List<GemBox> findAll(Long userId);
}
