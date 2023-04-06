package com.linkgem.domain.commonlink.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkgem.domain.commonlink.domain.CommonLink;

public interface CommonLinkRepository extends JpaRepository<CommonLink, Long> , CommonLinkRepositoryCustom{
}
