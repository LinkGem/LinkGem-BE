package com.linkgem.presentation.oauth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OauthRequest {

	@ApiModel(description = "회원 탈퇴 요청")
	@NoArgsConstructor
	@Getter
	public static class OauthLeaveRequest{

		@ApiModelProperty(value = "유저 고유 아이디")
		private Long userId;
		@ApiModelProperty(value = "Oauth 코드")
		private String code;
		@ApiModelProperty(value = "Oauth 제공자")
		private String providerName;
	}
}
