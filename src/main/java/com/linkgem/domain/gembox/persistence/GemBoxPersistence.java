package com.linkgem.domain.gembox.persistence;

import java.util.List;
import java.util.Optional;

import com.linkgem.domain.gembox.domain.GemBox;
import com.linkgem.domain.gembox.dto.GemBoxInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GemBoxPersistence {

    Optional<GemBox> find(String name, Long userId);

    Optional<GemBox> find(Long id);

    Optional<GemBox> find(Long id, Long userId);

    Optional<GemBox> findDefault(Long userId);

    GemBox get(Long id, Long userId);

    List<GemBox> findAll(Long userId);

    Page<GemBoxInfo.Search> search(Long userId, Pageable pageable);

    GemBox create(GemBox gembox);

    void delete(GemBox gemBox);

    void deleteAllByUserId(Long userId);
}