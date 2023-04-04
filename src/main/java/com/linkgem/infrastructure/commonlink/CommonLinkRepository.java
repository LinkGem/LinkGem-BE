package com.linkgem.infrastructure.commonlink;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkgem.domain.commonlink.CommonLink;

public interface CommonLinkRepository extends JpaRepository<CommonLink, Long> , CommonLinkRepositoryCustom{
}
