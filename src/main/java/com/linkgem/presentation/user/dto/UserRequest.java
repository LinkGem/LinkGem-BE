package com.linkgem.presentation.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {
  @ApiModel(description = "유저 정보 추가 요청")
  @Getter
  @NoArgsConstructor
  public static class AddDetailInfoRequest{

    private String jobName;

    private String userNickname;

    private Integer careerYear;

  }

}
