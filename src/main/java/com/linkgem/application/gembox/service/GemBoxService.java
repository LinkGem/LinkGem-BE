package com.linkgem.application.gembox.service;

import java.util.List;

import com.linkgem.presentation.gembox.cmd.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxInfo;
import com.linkgem.domain.gembox.GemBoxQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GemBoxService {

    GemBoxInfo.Create create(GemBoxCommand.Create command);

    void update(GemBoxCommand.Update command);

    List<GemBoxInfo.Main> findAll(Long userId);

    Page<GemBoxInfo.Search> search(Long userId, Pageable pageable);

    GemBoxInfo.Main find(GemBoxQuery.SearchDetail searchDetail);

    void delete(GemBoxCommand.Delete command);

    void deleteAllByUserId(Long userId);

    void putLinksToGembox(GemBoxCommand.PutLinksToGembox command);
}
