package com.linkgem.presentation.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.linkgem.domain.user.UserService;
import com.linkgem.presentation.common.UserAuthenticationProvider;
import com.linkgem.presentation.user.dto.UserRequest;
import com.linkgem.presentation.user.dto.UserRequest.AddDetailInfoRequest;
import com.linkgem.presentation.user.dto.UserResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(tags = "유저")
@RequestMapping("/api/v1/user")
public class UserApiController {

	private final UserService userService;

	@ApiOperation(value = "추가정보 추가", notes = "유저의 직업,경력,닉네임을 추가한다.")
	@PatchMapping("/addDetailInfo")
	public ResponseEntity<Void> addDetailInfo(
		HttpServletRequest httpServletRequest,
		@RequestBody AddDetailInfoRequest addDetailInfoRequest) {
		Long userId = UserAuthenticationProvider.provider(httpServletRequest);
		userService.addDetailInfo(userId, addDetailInfoRequest);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "유저 세팅 API", notes = "유저의 직업,경력,닉네임, 프로필사진을 추가한다.")
	@PostMapping("/settingUserInfo")
	public ResponseEntity<UserResponse.SettingResponse> settingUserInfo(HttpServletRequest httpServletRequest,
		@RequestParam MultipartFile profileImage,@RequestParam String nickName, @RequestParam String jobName,
		@RequestParam Integer careerYear) {
		Long userId = UserAuthenticationProvider.provider(httpServletRequest);
		UserResponse.SettingResponse settingResponse = userService.settingUserInfo(userId, profileImage, nickName,
			jobName, careerYear);
		return ResponseEntity.ok(settingResponse);
	}
}
