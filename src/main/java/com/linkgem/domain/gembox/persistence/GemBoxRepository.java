package com.linkgem.domain.gembox.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.gembox.domain.GemBox;

public interface GemBoxRepository extends JpaRepository<GemBox, Long>, GemBoxRepositoryCustom {
    Optional<GemBox> findByNameAndUserId(String name, Long userId);

    Optional<GemBox> findByIdAndUserId(Long id, Long userId);

    Optional<GemBox> findByUserIdAndIsDefault(Long userId, Boolean isDefault);

    List<GemBox> findAllByUserId(Long userId);

    @Transactional
    void deleteAllByUserId(Long userId);

}
