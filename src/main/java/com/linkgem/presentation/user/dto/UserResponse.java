package com.linkgem.presentation.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.linkgem.domain.user.UserPhase;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponse {

	@Getter
	@ApiModel(description = "사용자 Setting API 요청 응답")
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class SettingResponse {
		private Long userId;
		private String nickName;
		private String jobName;
		private Integer careerYear;
		private String profileImageUrl;

		public static SettingResponse of(Long userId,String nickName,String jobName, Integer careerYear, String profileImageUrl) {
			return SettingResponse.builder()
				.userId(userId)
				.nickName(nickName)
				.jobName(jobName)
				.careerYear(careerYear)
				.profileImageUrl(profileImageUrl)
				.build();
		}
	}

	@Getter
	@ApiModel(description = "로그인 요청 응답")
	@NoArgsConstructor
	public static class LoginResponse {
		private Long id;
		private String loginEmail;
		private String mailEmail;
		private String name;
		private String nickname;
		private String accessToken;
		private String refreshToken;
		private String jobName;
		private Integer careerYear;
		private String profileImageUrl;
		private String userPhase;

		@Builder
		public LoginResponse(Long id, String loginEmail, String nickname, String accessToken,
			String refreshToken, UserPhase userPhase, String mailEmail, String name,String jobName,Integer careerYear,String profileImageUrl) {
			this.id = id;
			this.nickname = nickname;
			this.name = name;
			this.loginEmail = loginEmail;
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
			this.userPhase = userPhase.name();
			this.mailEmail = mailEmail;
			this.careerYear = careerYear;
			this.jobName = jobName;
			this.profileImageUrl = profileImageUrl;
		}
	}

	@Getter
	@NoArgsConstructor
	public static class OauthTokenResponse {

		@JsonProperty("access_token")
		private String accessToken;

		private String scope;

		@JsonProperty("token_type")
		private String tokenType;

		@Builder
		public OauthTokenResponse(String accessToken, String scope, String tokenType) {
			this.accessToken = accessToken;
			this.scope = scope;
			this.tokenType = tokenType;
		}
	}

	@ApiModel(description = "엑세스 토큰 재발급 요청 응답")
	@Getter
	@NoArgsConstructor
	public static class TokenReissueResponse {

		private String accessToken;

		@Builder
		public TokenReissueResponse(String accessToken) {
			this.accessToken = accessToken;
		}
	}

	@ApiModel(description = "유저 탈퇴 요청 응답")
	@Getter
	@NoArgsConstructor
	public static class UserLeaveResponse {

		private String result;
		private String error;
		private String error_description;
	}

	@ApiModel(description = "유저 정보 요청 응답")
	@Getter
	@AllArgsConstructor
	@Builder
	public static class UserInfoResponse{
		private Long userId;
		private String loginEmail;
		private String mailEmail;
		private String name;
		private String nickname;
		private String job;
		private int careerYear;
		private String profileImageUrl;
	}



}
