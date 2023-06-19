package com.linkgem.domain.gembox;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GemBoxService {

    GemBoxInfo.Create create(GemBoxCommand.Create command);

    void update(GemBoxCommand.Update command);

    List<GemBoxInfo.Main> findAll(Long userId);

    Page<GemBoxInfo.Search> search(Long userId, Pageable pageable);

    GemBoxInfo.Main find(GemBoxQuery.SearchDetail searchDetail);

    void delete(GemBoxCommand.Delete command);

    void deleteAllByUserId(Long userId);

    void putLinksToGembox(GemBoxCommand.PutLinksToGembox command);

    void merge(GemBoxCommand.Merge command);
}
