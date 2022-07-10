package com.linkgem.presentation.gembox;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkgem.application.GemBoxFacade;
import com.linkgem.domain.gembox.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxInfo;
import com.linkgem.domain.gembox.GemBoxQuery;
import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.gembox.dto.GemBoxRequest;
import com.linkgem.presentation.gembox.dto.GemBoxResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = "잼박스")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/gemboxes")
@RestController
public class GemBoxApiController {

    private final GemBoxFacade gemBoxFacade;

    @ApiOperation(value = "잼박스 조회", notes = "잼박스를 조회한다")
    @GetMapping(value = "/{id}")
    public CommonResponse<GemBoxResponse.GemBox> find(
        @ApiParam(value = "잼박스 고유 아이디", example = "1") @PathVariable Long id
    ) {

        // :TODO User Id를 토큰에서 추출하는 방법을 결정해야한다.
        Long temporaryUserId = 1L;
        GemBoxInfo.Main gemboxInfo = gemBoxFacade.find(GemBoxQuery.SearchDetail.of(id, temporaryUserId));

        return CommonResponse.of(GemBoxResponse.GemBox.of(gemboxInfo));
    }

    @ApiOperation(value = "잼박스 목록 조회", notes = "잼박스 목록을 조회한다")
    @GetMapping
    public CommonResponse<List<GemBoxResponse.GemBox>> findAll() {

        // :TODO User Id를 토큰에서 추출하는 방법을 결정해야한다.
        Long temporaryUserId = 1L;
        List<GemBoxResponse.GemBox> responses = gemBoxFacade.findAll(temporaryUserId)
            .stream().map(GemBoxResponse.GemBox::of)
            .collect(Collectors.toList());

        return CommonResponse.of(responses);
    }

    @ApiOperation(value = "잼박스 생성", notes = "잼박스를 생성한다")
    @PostMapping
    public CommonResponse<GemBoxResponse.CreateResponse> create(
        @RequestBody @Valid GemBoxRequest.CreateRequest request) {

        // :TODO User Id를 토큰에서 추출하는 방법을 결정해야한다.
        Long temporaryUserId = 1L;
        GemBoxCommand.Create command = request.to(temporaryUserId);
        GemBoxInfo.Create createInfo = gemBoxFacade.create(command);

        return CommonResponse.of(GemBoxResponse.CreateResponse.of(createInfo));
    }

    @ApiOperation(value = "잼박스 수정", notes = "잼박스를 수정한다")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> update(
        @ApiParam(value = "잼박스 고유 아이디", example = "1") @PathVariable Long id,
        @RequestBody @Valid GemBoxRequest.UpdateRequest request) {

        // :TODO User Id를 토큰에서 추출하는 방법을 결정해야한다.
        Long temporaryUserId = 1L;
        GemBoxCommand.Update command = request.to(temporaryUserId, id);
        gemBoxFacade.update(command);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "잼박스 삭제", notes = "잼박스를 삭제한다")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
        @ApiParam(value = "잼박스 고유 아이디", example = "1") @PathVariable Long id
    ) {

        // :TODO User Id를 토큰에서 추출하는 방법을 결정해야한다.
        Long temporaryUserId = 1L;
        gemBoxFacade.delete(GemBoxCommand.Delete.of(id, temporaryUserId));

        return ResponseEntity.noContent().build();
    }
}
