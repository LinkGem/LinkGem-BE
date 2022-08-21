package com.linkgem.infrastructure.gembox;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkgem.domain.gembox.GemBox;

public interface GemBoxRepository extends JpaRepository<GemBox, Long>, GemBoxRepositoryCustom {
    Optional<GemBox> findByNameAndUserId(String name, Long userId);

    Optional<GemBox> findByIdAndUserId(Long id, Long userId);

    List<GemBox> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);

}
