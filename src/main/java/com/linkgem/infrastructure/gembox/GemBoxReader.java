package com.linkgem.infrastructure.gembox;

import java.util.List;
import java.util.Optional;

import com.linkgem.domain.gembox.GemBox;
import com.linkgem.domain.gembox.GemBoxInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GemBoxReader {

    Optional<GemBox> find(String name, Long userId);

    Optional<GemBox> find(Long id);

    Optional<GemBox> find(Long id, Long userId);

    Optional<GemBox> findDefault(Long userId);

    GemBox get(Long id, Long userId);

    List<GemBox> findAll(Long userId);

    Page<GemBoxInfo.Search> search(Long userId, Pageable pageable);
}
