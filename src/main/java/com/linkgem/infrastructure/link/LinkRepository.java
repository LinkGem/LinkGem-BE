package com.linkgem.infrastructure.link;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkgem.domain.link.Link;

public interface LinkRepository extends JpaRepository<Link, Long> ,LinkRepositoryCustom{
}
