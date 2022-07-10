package com.linkgem.infrastructure.link;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkgem.domain.link.Link;

public interface LinkRepository extends JpaRepository<Link, Long>, LinkRepositoryCustom {

    Optional<Link> findByIdAndUserId(Long id, Long userId);
}
