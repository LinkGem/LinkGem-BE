package com.linkgem.presentation.oauth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkgem.domain.oauth.OauthService;
import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.oauth.dto.OauthRequest;
import com.linkgem.presentation.oauth.dto.OauthResponse;
import com.linkgem.presentation.oauth.dto.OauthResponse.LoginResponse;
import com.linkgem.presentation.oauth.dto.OauthResponse.TokenReissueResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@Api(tags = "Oauth")
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OauthRestApiController {

	private final OauthService oauthService;

	@ApiOperation(value = "소셜로그인", notes = "소셜 로그인을 진행한다.")
	@GetMapping("/login/{provider}")
	public ResponseEntity<CommonResponse<LoginResponse>> login(
		@ApiParam(value = "provider", example = "naver") @PathVariable String provider,
		@ApiParam(value = "인증코드") @RequestParam String code) {
		LoginResponse loginResponse = oauthService.login(provider, code);
		return ResponseEntity.ok(CommonResponse.of(loginResponse));
	}

	@ApiOperation(value = "Access-Token 재발급", notes = "Access-Token을 재발급한다.")
	@PostMapping("/reissue")
	public ResponseEntity<CommonResponse<TokenReissueResponse>> reissue(
		@RequestHeader(value = "ACCESS-TOKEN") String accessToken,
		@RequestHeader(value = "REFRESH-TOKEN") String refreshToken) {
		TokenReissueResponse tokenReissueResponse = oauthService.reissue(accessToken, refreshToken);
		return ResponseEntity.ok((CommonResponse.of(tokenReissueResponse)));
	}

	public OauthResponse.OauthLeaveResponse leave(@RequestBody OauthRequest.OauthLeaveRequest oauthLeaveRequest) {
		return null;
	}

}
