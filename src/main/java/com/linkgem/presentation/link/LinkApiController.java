package com.linkgem.presentation.link;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkgem.application.LinkFacade;
import com.linkgem.domain.link.LinkCommand;
import com.linkgem.domain.link.LinkInfo;
import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.link.dto.LinkRequest;
import com.linkgem.presentation.link.dto.LinkResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "링크")
@RequiredArgsConstructor
@RequestMapping(value = "/api/link")
@RestController
public class LinkApiController {

    private final LinkFacade linkFacade;

    @ApiOperation(value = "링크 생성", notes = "링크를 생성한다")
    @PostMapping
    public CommonResponse<LinkResponse.CreateResponse> createLink(
        @RequestBody @Valid LinkRequest.CreateRequest request
    ) {
        Long userId = 1L;
        LinkCommand.Create createCommand = request.to(userId);

        LinkInfo.Create create = linkFacade.create(createCommand);

        return CommonResponse.of(LinkResponse.CreateResponse.of(create));
    }

    @ApiOperation(value = "링크 목록 조회", notes = "링크를 목록을 조회한다")
    @GetMapping
    public CommonResponse<Page<LinkResponse.SearchResponse>> findAll(
        @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Long userId = 1L;

        Page<LinkInfo.Search> infos = linkFacade.findAll(userId, pageable);

        return CommonResponse.of(infos.map(LinkResponse.SearchResponse::of));
    }

}