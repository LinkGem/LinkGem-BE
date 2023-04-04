package com.linkgem.domain.gembox;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GemBoxService {

    GemBoxInfo.Create create(GemBoxCommand.Create command);

    void update(GemBoxCommand.Update command);

    List<GemBoxInfo.Main> findAll(Long userId);

    Page<GemBoxInfo.Search> search(Long userId, Pageable pageable);

    GemBoxInfo.Main find(GemBoxCommand.SearchDetail searchDetail);

    void delete(GemBoxCommand.Delete command);

    void deleteAllByUserId(Long userId);

    void putLinksToGembox(GemBoxCommand.PutLinksToGembox command);

    boolean isExisted(GemBoxCommand.SearchDuplication searchDuplication);

    boolean isFull(Long userId);
}
