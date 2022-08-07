package com.linkgem.presentation.link;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkgem.application.LinkFacade;
import com.linkgem.domain.common.Pages;
import com.linkgem.domain.link.LinkCommand;
import com.linkgem.domain.link.LinkInfo;
import com.linkgem.domain.link.LinkQuery;
import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.common.UserAuthenticationProvider;
import com.linkgem.presentation.link.dto.LinkRequest;
import com.linkgem.presentation.link.dto.LinkResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "링크")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/links")
@RestController
public class LinkApiController {

    private final LinkFacade linkFacade;

    @ApiOperation(value = "링크 생성", notes = "링크를 생성한다")
    @PostMapping
    public CommonResponse<LinkResponse.CreateLinkResponse> createLink(
        HttpServletRequest httpServletRequest,
        @RequestBody @Valid LinkRequest.CreateLinkRequest request
    ) {
        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        LinkCommand.Create createCommand = request.to(userId);

        LinkInfo.Create create = linkFacade.create(createCommand);

        return CommonResponse.of(LinkResponse.CreateLinkResponse.of(create));
    }

    @ApiOperation(value = "링크 상세 조회", notes = "링크를 상세하게 조회한다")
    @GetMapping(value = "/{id}")
    public CommonResponse<LinkResponse.SearchDetailLinkResponse> findById(
        HttpServletRequest httpServletRequest,
        @PathVariable(value = "id") Long id
    ) {
        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        LinkInfo.Detail detailLink = linkFacade.findById(id, userId);
        LinkResponse.SearchDetailLinkResponse linkResponse = LinkResponse.SearchDetailLinkResponse.of(detailLink);
        return CommonResponse.of(linkResponse);
    }

    @ApiOperation(value = "링크 목록 조회", notes = "링크 목록을 조회한다")
    @GetMapping
    public CommonResponse<Pages<LinkResponse.SearchLinkResponse>> findAll(
        HttpServletRequest httpServletRequest,
        @PageableDefault(page = 0, size = 10) Pageable pageable,
        LinkRequest.SearchLinksRequest request
    ) {
        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        LinkQuery.SearchLinks searchLinks = request.to(userId);

        Page<LinkInfo.Search> infos = linkFacade.findAll(searchLinks, pageable);
        Page<LinkResponse.SearchLinkResponse> responses = infos.map(LinkResponse.SearchLinkResponse::of);

        return CommonResponse.of(
            Pages.<LinkResponse.SearchLinkResponse>builder()
                .contents(responses.getContent())
                .totalCount(responses.getTotalElements())
                .size(pageable.getPageSize())
                .page(pageable.getPageNumber())
                .build()
        );
    }

    @ApiOperation(value = "링크 삭제", notes = "링크를 삭제한다")
    @DeleteMapping
    public CommonResponse<LinkResponse.DeleteLinkResponse> deleteLinks(
        HttpServletRequest httpServletRequest,
        @RequestBody @Valid LinkRequest.DeleteLinkRequest request
    ) {
        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        LinkCommand.Delete deleteCommand = request.to(userId);
        List<Long> deleteIds = linkFacade.deletes(deleteCommand);

        return CommonResponse.of(new LinkResponse.DeleteLinkResponse(deleteIds));

    }

    @ApiOperation(value = "링크 수정", notes = "링크를 수정한다")
    @PatchMapping(value = "/{id}")
    public CommonResponse<LinkResponse.Main> updateLink(
        HttpServletRequest httpServletRequest,
        @PathVariable Long id,
        @RequestBody @Valid LinkRequest.UpdateLinkRequest request
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        LinkCommand.Update updateCommand = request.to(id, userId);
        LinkInfo.Main updateInfo = linkFacade.update(updateCommand);

        return CommonResponse.of(LinkResponse.Main.of(updateInfo));
    }
}
