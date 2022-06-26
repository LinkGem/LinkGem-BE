package com.linkgem.presentation.gembox;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkgem.application.GemBoxFacade;
import com.linkgem.domain.gembox.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxInfo;
import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.gembox.dto.GemBoxRequest;
import com.linkgem.presentation.gembox.dto.GemBoxResponse;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value = "/api/gembox")
@RestController
public class GemBoxApiController {

    private final GemBoxFacade gemBoxFacade;

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

    @ApiOperation(value = "잼박스 조회", notes = "잼박스를 조회한다")
    @GetMapping
    public CommonResponse<List<GemBoxResponse.GemBox>> findAll(
    ) {

        // :TODO User Id를 토큰에서 추출하는 방법을 결정해야한다.
        Long temporaryUserId = 1L;
        List<GemBoxResponse.GemBox> responses = gemBoxFacade.findAll(temporaryUserId)
            .stream().map(GemBoxResponse.GemBox::of)
            .collect(Collectors.toList());

        return CommonResponse.of(responses);
    }

}
