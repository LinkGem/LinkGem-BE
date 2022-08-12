package com.linkgem.domain.user;

import static com.linkgem.presentation.user.dto.UserRequest.*;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.linkgem.presentation.user.dto.UserRequest;
import com.linkgem.presentation.user.dto.UserResponse;

public interface UserService {

	void addDetailInfo(Long userId, AddDetailInfoRequest addDetailInfoRequest);

	 UserResponse.SettingResponse settingUserInfo(Long userId, MultipartFile profileImage, String nickName, String jobName, Integer careerYear);

}
