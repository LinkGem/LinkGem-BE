package com.linkgem.domain.user;

import lombok.Builder;
import lombok.Getter;

public class UserInfo {

	@Getter
	public static class UserProfile{
		private final String oauthId;
		private final String loginEmail;
		private final String name;

		@Builder
		public UserProfile(String oauthId, String loginEmail, String name) {
			this.oauthId = oauthId;
			this.loginEmail = loginEmail;
			this.name = name;
		}

		public User toUser() {
			return User.builder()
				.loginEmail(loginEmail)
				.name(name)
				.oauthId(oauthId)
				.build();
		}
	}
}
