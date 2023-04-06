package com.linkgem.domain.link.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.link.domain.Link;

public interface LinkRepository extends JpaRepository<Link, Long>, LinkRepositoryCustom {

    Optional<Link> findByIdAndUserId(Long id, Long userId);

    List<Link> findAllByUserId(Long userId);

    List<Link> findAllByGemBoxId(Long gemBoxId);
    @Transactional
    void deleteAllByUserId(Long userId);
}
