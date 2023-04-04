package com.linkgem.presentation.user;

import javax.servlet.http.HttpServletRequest;

import com.linkgem.domain.user.UserLeaveService;
import com.linkgem.domain.user.UserLoginService;
import com.linkgem.domain.user.UserService;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.common.UserAuthenticationProvider;
import com.linkgem.presentation.user.dto.UserRequest;
import com.linkgem.presentation.user.dto.UserRequest.AddDetailInfoRequest;
import com.linkgem.presentation.user.dto.UserResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "유저")
@RequestMapping("/api/v1/user")
public class UserApiController {
	private final UserService userService;

	private final List<UserLoginService> userLoginServices;

	private final List<UserLeaveService> userLeaveServices;

	@ApiOperation(value = "추가정보 추가", notes = "유저의 직업,경력,닉네임을 추가한다.")
	@PatchMapping("/addDetailInfo")
	public ResponseEntity<CommonResponse<Void>> addDetailInfo(
		HttpServletRequest httpServletRequest,
		@RequestBody AddDetailInfoRequest addDetailInfoRequest) {
		Long userId = UserAuthenticationProvider.provider(httpServletRequest);
		userService.addDetailInfo(userId, addDetailInfoRequest);
		return ResponseEntity.ok(CommonResponse.of(null));
	}

	@ApiOperation(value = "유저 세팅 API", notes = "유저의 직업,경력,닉네임, 프로필사진을 추가한다.")
	@PostMapping("/settingUserInfo")
	public ResponseEntity<CommonResponse<UserResponse.SettingResponse>> settingUserInfo(HttpServletRequest httpServletRequest,
		@RequestParam(required = false) MultipartFile profileImage, @RequestParam String nickName,
		@RequestParam String jobName,
		@RequestParam Integer careerYear) {
		Long userId = UserAuthenticationProvider.provider(httpServletRequest);
		UserResponse.SettingResponse settingResponse = userService.settingUserInfo(userId, profileImage, nickName,
			jobName, careerYear);
		return ResponseEntity.ok(CommonResponse.of(settingResponse));
	}

	@ApiOperation(value = "소셜로그인", notes = "소셜 로그인을 진행한다.")
	@GetMapping("oauth/login/{provider}")
	public ResponseEntity<CommonResponse<UserResponse.LoginResponse>> login(
		@ApiParam(value = "provider", example = "naver") @PathVariable String provider,
		@ApiParam(value = "인증코드") @RequestParam String code) {
		UserLoginService userLoginService = userLoginServices.stream()
				.filter(p -> p.getLoginService(provider))
				.findFirst()
				.orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_VALID));
		UserResponse.LoginResponse loginResponse = userLoginService.login(provider, code);

		return ResponseEntity.ok(CommonResponse.of(loginResponse));
	}

	@ApiOperation(value = "Access-Token 재발급", notes = "Access-Token을 재발급한다.")
	@PostMapping("oauth/reissue")
	public ResponseEntity<CommonResponse<UserResponse.TokenReissueResponse>> reissue(
		@RequestHeader(value = "ACCESS-TOKEN") String accessToken,
		@RequestHeader(value = "REFRESH-TOKEN") String refreshToken) {
		UserResponse.TokenReissueResponse tokenReissueResponse = userService.reissue(accessToken, refreshToken);
		return ResponseEntity.ok(CommonResponse.of(tokenReissueResponse));
	}

	@ApiOperation(value = "유저 탈퇴", notes = "유저 탈퇴를 진행한다.")
	@PostMapping("oauth/leave")
	public ResponseEntity<CommonResponse<UserResponse.UserLeaveResponse>> leave(
		@RequestBody UserRequest.UserLeaveRequest userLeaveRequest) {
		UserLeaveService userLeaveService = userLeaveServices.stream()
				.filter(p -> p.getLeaveService(userLeaveRequest.getProviderName()))
				.findFirst()
				.orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_VALID));
		UserResponse.UserLeaveResponse userLeaveResponse = userLeaveService.leave(userLeaveRequest);
		return ResponseEntity.ok(CommonResponse.of(userLeaveResponse));
	}

	@ApiOperation(value = "유저 정보 API", notes = "유저 정보를 반환한다.")
	@GetMapping("/info")
	public ResponseEntity<CommonResponse<UserResponse.UserInfoResponse>> info(HttpServletRequest httpServletRequest){
		Long userId = UserAuthenticationProvider.provider(httpServletRequest);
		return ResponseEntity.ok(CommonResponse.of(userService.info(userId)));
	}
}
