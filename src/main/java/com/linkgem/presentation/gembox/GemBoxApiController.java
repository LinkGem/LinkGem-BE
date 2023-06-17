package com.linkgem.presentation.gembox;

import com.linkgem.application.GemBoxFacade;
import com.linkgem.domain.common.Pages;
import com.linkgem.domain.gembox.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxInfo;
import com.linkgem.domain.gembox.GemBoxQuery;
import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.common.UserAuthenticationProvider;
import com.linkgem.presentation.gembox.dto.GemBoxRequest;
import com.linkgem.presentation.gembox.dto.GemBoxResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "잼박스")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/gemboxes")
@RestController
public class GemBoxApiController {

    private final GemBoxFacade gemBoxFacade;

    @ApiOperation(value = "잼박스 조회", notes = "잼박스를 조회한다")
    @GetMapping(value = "/{id}")
    public CommonResponse<GemBoxResponse.Main> find(
        HttpServletRequest httpServletRequest,
        @ApiParam(value = "잼박스 고유 아이디", example = "1") @PathVariable Long id
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        GemBoxInfo.Main gemboxInfo = gemBoxFacade.find(GemBoxQuery.SearchDetail.of(id, userId));

        return CommonResponse.of(GemBoxResponse.Main.of(gemboxInfo));
    }

    @ApiOperation(value = "잼박스 목록 조회", notes = "잼박스 목록을 조회한다")
    @GetMapping
    public CommonResponse<Pages<GemBoxResponse.Search>> findAll(
        HttpServletRequest httpServletRequest,
        Pageable pageable
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        Page<GemBoxResponse.Search> responses = gemBoxFacade.search(userId, pageable)
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
        GemBoxInfo.Create createInfo = gemBoxFacade.create(command);

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
        gemBoxFacade.update(command);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "잼박스 삭제", notes = "잼박스를 삭제한다")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
        HttpServletRequest httpServletRequest,
        @ApiParam(value = "잼박스 고유 아이디", example = "1") @PathVariable Long id
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        gemBoxFacade.delete(GemBoxCommand.Delete.of(id, userId));

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

        gemBoxFacade.putLinksToGembox(command);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "선택 된 잼박스를 합칠 잼 박스에 넣는다.", notes = "개별 잼 박스를 합친다.")
    @PatchMapping(value = "/{targetId}/merge")
    public ResponseEntity<Void> mergeGemBox(
        HttpServletRequest httpServletRequest,
        @ApiParam(value = "선택 된 잼박스 고유 아이디", example = "1")  @PathVariable Long targetId,
        @ApiParam(value = "합칠 잼박스 고유 아이디", example = "1") @RequestParam Long sourceId
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);
        gemBoxFacade.merge(GemBoxCommand.Merge.of(userId, targetId, sourceId));

        return ResponseEntity.noContent().build();
    }

}
