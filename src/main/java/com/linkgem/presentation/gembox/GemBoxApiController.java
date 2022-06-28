package com.linkgem.presentation.gembox;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkgem.application.GemBoxFacade;
import com.linkgem.domain.gembox.GemBoxCommand;
import com.linkgem.domain.gembox.GemBoxInfo;
import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.gembox.dto.GemBoxCreateRequest;
import com.linkgem.presentation.gembox.dto.GemBoxCreateResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value = "/api/gembox")
@RestController
public class GemBoxApiController {

    private final GemBoxFacade gemBoxFacade;

    @PostMapping
    public CommonResponse<GemBoxCreateResponse> create(@RequestBody @Valid GemBoxCreateRequest request) {

        // :TODO User Id를 토큰에서 추출하는 방법을 결정해야한다.

        Long temporaryUserId = 1L;
        GemBoxCommand.Create command = request.to(temporaryUserId);
        GemBoxInfo.Create createInfo = gemBoxFacade.create(command);

        return CommonResponse.of(GemBoxCreateResponse.of(createInfo));
    }

}
