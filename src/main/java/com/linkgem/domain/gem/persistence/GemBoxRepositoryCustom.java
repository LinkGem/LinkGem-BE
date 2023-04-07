package com.linkgem.domain.gem.persistence;

import com.linkgem.domain.gem.dto.GemBoxInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GemBoxRepositoryCustom {
    Page<GemBoxInfo.Search> search(Long userId, Pageable pageable);

}
