package com.linkgem.domain.user.service;

import com.linkgem.domain.user.domain.User;
import com.linkgem.domain.user.dto.UserRequest;
import com.linkgem.domain.user.dto.UserResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void initialize(User user);

    UserResponse.UserInfoResponse info(Long userId);

    void addDetailInfo(Long userId, UserRequest.AddDetailInfoRequest addDetailInfoRequest);

    UserResponse.SettingResponse settingUserInfo(Long userId, MultipartFile profileImage, String nickName, String jobName, Integer careerYear);

    UserResponse.TokenReissueResponse reissue(String accessToken,String refreshToken);
}
