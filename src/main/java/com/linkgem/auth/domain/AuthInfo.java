package com.linkgem.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class AuthInfo {

	@Builder
	@AllArgsConstructor
	@Getter
	public static class MailConfirm{
		private boolean mailConfirm;

		public static MailConfirm of(boolean mailConfirm){
			return MailConfirm.builder()
				.mailConfirm(mailConfirm)
				.build();
		}
	}

}
