package com.linkgem.domain.gem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.linkgem.domain.gem.dto.CommonLinkCommand;
import com.linkgem.domain.gem.service.CommonLinkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkgem.domain.common.Pages;
import com.linkgem.domain.gem.dto.CommonLinkInfo;
import com.linkgem.domain.common.controller.CommonResponse;
import com.linkgem.domain.gem.dto.CommonLinkRequest;
import com.linkgem.domain.gem.dto.CommonLinkResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "잼크루 픽")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/gem/gem-crew-link")
@RestController
public class CommonLinkApiController {

    private final CommonLinkService commonLinkService;

    @ApiOperation(value = "잼크루 픽 생성", notes = "잼크루 픽을 생성한다")
    @PostMapping
    public CommonResponse<CommonLinkResponse.Main> create(
        HttpServletRequest httpServletRequest,
        @RequestBody @Valid CommonLinkRequest.Create request
    ) {
        CommonLinkInfo.Main main = commonLinkService.create(request.to());

        return CommonResponse.of(CommonLinkResponse.Main.of(main));
    }

    @ApiOperation(value = "잼크루 픽 삭제", notes = "잼크루 픽을 삭제한다")
    @DeleteMapping
    public ResponseEntity<Void> deleteOne(
        HttpServletRequest httpServletRequest,
        @RequestBody @Valid CommonLinkRequest.DeleteOne request
    ) {
        commonLinkService.delete(request.to());
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "잼크루 픽 목록 조회", notes = "잼크루 픽 목록 조회한다")
    @GetMapping
    public CommonResponse<Pages<CommonLinkResponse.Main>> findAll(
        HttpServletRequest httpServletRequest,
        @PageableDefault(page = 0, size = 10) Pageable pageable,
        @Valid CommonLinkRequest.FindAll request
    ) {
        CommonLinkCommand.FindAll findAllQuery = request.to();

        Page<CommonLinkInfo.Main> commonLinkInfos = commonLinkService.findAll(findAllQuery, pageable);
        List<CommonLinkResponse.Main> responses = CommonLinkResponse.Main.ofs(commonLinkInfos.getContent());

        return CommonResponse.of(
            Pages.<CommonLinkResponse.Main>builder()
                .contents(responses)
                .totalCount(commonLinkInfos.getTotalElements())
                .size(pageable.getPageSize())
                .page(pageable.getPageNumber())
                .build()
        );
    }
}
