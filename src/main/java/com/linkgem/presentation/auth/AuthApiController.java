package com.linkgem.presentation.auth;

import com.linkgem.domain.auth.AuthInfo;
import com.linkgem.application.auth.service.MailAuthService;
import com.linkgem.presentation.auth.dto.AuthResponse;
import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.common.UserAuthenticationProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = "인증")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthApiController {

	private final MailAuthService mailAuthService;

	@ApiOperation(value = "인증 메일 전송", notes = "인증 메일을 전송한다.")
	@PostMapping("/mail/send")
	public ResponseEntity<CommonResponse<Void>> sendMail(HttpServletRequest httpServletRequest, @RequestParam String emailAddress) {
		Long userId = UserAuthenticationProvider.provider(httpServletRequest);
		mailAuthService.mailSend(emailAddress, userId);
		return ResponseEntity.ok(CommonResponse.of(null));

	}

	@ApiOperation(value = "메일 체크", notes = "메일 체크(메일에서 좋아요 버튼 누를 시)")
	@PostMapping("/mail/check")
	public ResponseEntity<Void> mailAuth(@RequestParam String randomId,@RequestParam Long userId) {
		URI uri = mailAuthService.mailCheck(userId,randomId);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	@ApiOperation(value = "메일 인증여부 확인", notes = "유저의 메일 인증여부를 반환한다.")
	@GetMapping("/mail/confirm")
	public ResponseEntity<CommonResponse<AuthResponse.MailConfirmResponse>> mailConfirm(HttpServletRequest httpServletRequest,
		@RequestParam String emailAddress){
		Long userId = UserAuthenticationProvider.provider(httpServletRequest);
		boolean auth = mailAuthService.mailConfirm(emailAddress, userId);
		AuthInfo.MailConfirm mailConfirm = AuthInfo.MailConfirm.of(auth);
		AuthResponse.MailConfirmResponse mailConfirmResponse = AuthResponse.MailConfirmResponse.of(mailConfirm);
		return ResponseEntity.ok(CommonResponse.of(mailConfirmResponse));

	}

}
