package com.linkgem.application.user.facade;

import java.util.List;

import com.linkgem.application.user.service.UserInfoService;
import com.linkgem.application.user.service.UserLeaveService;
import com.linkgem.application.user.service.UserLoginService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.linkgem.application.user.service.UserSettingService;
import com.linkgem.application.user.service.UserTokenReissueService;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import com.linkgem.presentation.user.dto.UserRequest;
import com.linkgem.presentation.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserFacade {

  private final UserSettingService userSettingService;
  private final List<UserLeaveService> userLeaveServices;
  private final UserTokenReissueService userTokenReissueService;
  private final List<UserLoginService> userLoginServices;

  private final UserInfoService userInfoService;

  public UserResponse.LoginResponse login(String provider, String code){
    UserLoginService userLoginService = userLoginServices.stream()
        .filter(p -> p.getLoginService(provider))
        .findFirst()
        .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_VALID));
    return userLoginService.login(provider, code);

  }


  public UserResponse.TokenReissueResponse reissue(String accessToken,String refreshToken){
    return userTokenReissueService.reissue(accessToken, refreshToken);
  }

  public UserResponse.UserLeaveResponse leave(UserRequest.UserLeaveRequest userLeaveRequest){
    UserLeaveService userLeaveService = userLeaveServices.stream()
        .filter(p -> p.getLeaveService(userLeaveRequest.getProviderName()))
        .findFirst()
        .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_VALID));
    return userLeaveService.leave(userLeaveRequest);
  }

  public void addDetailInfo(Long userId,UserRequest.AddDetailInfoRequest addDetailInfoRequest){
    userSettingService.addDetailInfo(userId,addDetailInfoRequest);
  }

  public UserResponse.SettingResponse settingUserInfo(Long userId, MultipartFile profileImage, String nickName, String jobName, Integer careerYear){
    return userSettingService.settingUserInfo(userId, profileImage, nickName,
        jobName, careerYear);
  }

  public UserResponse.UserInfoResponse info(Long userId){
    return userInfoService.info(userId);
  }


}
