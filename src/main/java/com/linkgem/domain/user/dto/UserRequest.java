package com.linkgem.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {
	@ApiModel(description = "유저 정보 추가 요청")
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class AddDetailInfoRequest {

		private String jobName;

		private String userNickname;

		private Integer careerYear;

	}

	@ApiModel(description = "회원 탈퇴 요청")
	@NoArgsConstructor
	@Getter
	public static class UserLeaveRequest{

		@ApiModelProperty(value = "유저 고유 아이디")
		private Long userId;
		@ApiModelProperty(value = "Oauth 코드")
		private String code;
		@ApiModelProperty(value = "Oauth 제공자")
		private String providerName;
	}

}
