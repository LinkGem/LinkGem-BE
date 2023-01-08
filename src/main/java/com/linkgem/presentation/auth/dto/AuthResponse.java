package com.linkgem.presentation.auth.dto;

import com.linkgem.domain.auth.AuthInfo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class AuthResponse {

	@ApiModel(description = "메일 인증 응답")
	@AllArgsConstructor
	@Getter
	public static class MailConfirmResponse {
		private boolean auth;

		public static MailConfirmResponse of(AuthInfo.MailConfirm mailConfirm){
			return new MailConfirmResponse(mailConfirm.isMailConfirm());
	}

	}

}
