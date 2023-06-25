package com.linkgem.presentation.open;

import com.linkgem.application.GemBoxFacade;
import com.linkgem.application.LinkFacade;
import com.linkgem.domain.common.Pages;
import com.linkgem.domain.gembox.GemBoxInfo;
import com.linkgem.domain.gembox.GemBoxQuery;
import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkQuery;
import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.gembox.dto.GemBoxResponse;
import com.linkgem.presentation.link.dto.LinkRequest;
import com.linkgem.presentation.link.dto.LinkResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "퍼블릭")
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/open")
@RestController
public class OpenLinkApiController {

    private final LinkFacade linkFacade;
    private final GemBoxFacade gemBoxFacade;

    @ApiOperation(value = "공유 링크 목록 조회", notes = "공유 가능한 링크 목록을 조회한다")
    @GetMapping("/links")
    public CommonResponse<Pages<LinkResponse.SearchLinkResponse>> findAll(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @Valid LinkRequest.SearchLinksRequest request
    ) {
        LinkQuery.SearchLinks searchLinks = request.to(null);

        Page<LinkInfo.Search> infos = linkFacade.findAll(searchLinks, pageable);
        Page<LinkResponse.SearchLinkResponse> responses = infos.map(info
                -> LinkResponse.SearchLinkResponse.of(info, true));

        return CommonResponse.of(
                Pages.<LinkResponse.SearchLinkResponse>builder()
                        .contents(responses.getContent())
                        .totalCount(responses.getTotalElements())
                        .size(pageable.getPageSize())
                        .page(pageable.getPageNumber())
                        .build()
        );
    }

    @ApiOperation(value = "공유 잼박스 조회", notes = "공유 잼박스를 조회한다")
    @GetMapping(value = "/gemboxes/{id}")
    public CommonResponse<GemBoxResponse.Main> find(
            @ApiParam(value = "잼박스 고유 아이디", example = "1") @PathVariable Long id
    ) {

        GemBoxInfo.Main gemboxInfo = gemBoxFacade.findById(GemBoxQuery.SearchDetail.of(id, null));
        return CommonResponse.of(GemBoxResponse.Main.of(gemboxInfo));
    }
}
