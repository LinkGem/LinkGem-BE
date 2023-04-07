package com.linkgem.domain.gem.persistence;

import java.util.List;
import java.util.Optional;

import com.linkgem.domain.gem.domain.GemBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface GemBoxRepository extends JpaRepository<GemBox, Long>, GemBoxRepositoryCustom {
    Optional<GemBox> findByNameAndUserId(String name, Long userId);

    Optional<GemBox> findByIdAndUserId(Long id, Long userId);

    Optional<GemBox> findByUserIdAndIsDefault(Long userId, Boolean isDefault);

    List<GemBox> findAllByUserId(Long userId);

    @Transactional
    void deleteAllByUserId(Long userId);

}
