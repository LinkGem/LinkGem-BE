package com.linkgem.domain.gembox.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.linkgem.domain.gembox.service.GemBoxService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkgem.domain.common.Pages;
import com.linkgem.domain.gembox.dto.GemBoxCommand;
import com.linkgem.domain.gembox.dto.GemBoxInfo;
import com.linkgem.domain.common.controller.CommonResponse;
import com.linkgem.domain.common.UserAuthenticationProvider;
import com.linkgem.domain.gembox.dto.GemBoxRequest;
import com.linkgem.domain.gembox.dto.GemBoxResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = "잼박스")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/gemboxes")
@RestController
public class GemBoxApiController {

    private final GemBoxService gemBoxService;

    @ApiOperation(value = "잼박스 조회", notes = "잼박스를 조회한다")
    @GetMapping(value = "/{id}")
    public CommonResponse<GemBoxResponse.Main> find(
        HttpServletRequest httpServletRequest,
        @ApiParam(value = "잼박스 고유 아이디", example = "1") @PathVariable Long id
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        GemBoxInfo.Main gemboxInfo = gemBoxService.find(GemBoxCommand.SearchDetail.of(id, userId));

        return CommonResponse.of(GemBoxResponse.Main.of(gemboxInfo));
    }

    @ApiOperation(value = "잼박스 목록 조회", notes = "잼박스 목록을 조회한다")
    @GetMapping
    public CommonResponse<Pages<GemBoxResponse.Search>> findAll(
        HttpServletRequest httpServletRequest,
        Pageable pageable
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        Page<GemBoxResponse.Search> responses = gemBoxService.search(userId, pageable)
            .map(GemBoxResponse.Search::of);

        return CommonResponse.of(
            Pages.<GemBoxResponse.Search>builder()
                .contents(responses.getContent())
                .totalCount(responses.getTotalElements())
                .size(pageable.getPageSize())
                .page(pageable.getPageNumber())
                .build()
        );
    }

    @ApiOperation(value = "잼박스 생성", notes = "잼박스를 생성한다")
    @PostMapping
    public CommonResponse<GemBoxResponse.CreateGemboxResponse> create(
        HttpServletRequest httpServletRequest,
        @RequestBody @Valid GemBoxRequest.CreateGemBoxRequest request
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        GemBoxCommand.Create command = request.to(userId);
        GemBoxInfo.Create createInfo = gemBoxService.create(command);

        return CommonResponse.of(GemBoxResponse.CreateGemboxResponse.of(createInfo));
    }

    @ApiOperation(value = "잼박스 수정", notes = "잼박스를 수정한다")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> update(
        HttpServletRequest httpServletRequest,
        @ApiParam(value = "잼박스 고유 아이디", example = "1") @PathVariable Long id,
        @RequestBody @Valid GemBoxRequest.UpdateGemboxRequest request
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        GemBoxCommand.Update command = request.to(userId, id);
        gemBoxService.update(command);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "잼박스 삭제", notes = "잼박스를 삭제한다")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
        HttpServletRequest httpServletRequest,
        @ApiParam(value = "잼박스 고유 아이디", example = "1") @PathVariable Long id
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        gemBoxService.delete(GemBoxCommand.Delete.of(id, userId));

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "링크들을 잼박스에 넣는다.", notes = "링크들을 잼박스에 넣는다.")
    @PatchMapping(value = "/{id}/links")
    public ResponseEntity<Void> pushLinksToGembox(
        HttpServletRequest httpServletRequest,
        @ApiParam(value = "잼박스 고유 아이디", example = "1") @PathVariable Long id,
        @RequestBody @Valid GemBoxRequest.PutLinksToGemboxRequest request
    ) {
        Long userId = UserAuthenticationProvider.provider(httpServletRequest);

        GemBoxCommand.PutLinksToGembox command = GemBoxCommand.PutLinksToGembox.builder()
            .userId(userId)
            .gemBoxId(id)
            .linkIds(request.getLinks())
            .build();

        gemBoxService.putLinksToGembox(command);
        return ResponseEntity.noContent().build();
    }
}
