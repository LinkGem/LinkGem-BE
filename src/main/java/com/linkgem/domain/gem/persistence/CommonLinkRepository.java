package com.linkgem.domain.gem.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkgem.domain.gem.domain.CommonLink;

public interface CommonLinkRepository extends JpaRepository<CommonLink, Long> , CommonLinkRepositoryCustom{
}
