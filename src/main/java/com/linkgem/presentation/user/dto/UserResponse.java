package com.linkgem.presentation.user.dto;

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
}
