package com.linkgem.domain.user;

import com.linkgem.presentation.user.dto.UserRequest;
import com.linkgem.presentation.user.dto.UserResponse;

public interface UserLeaveService {

	UserResponse.UserLeaveResponse leave(UserRequest.UserLeaveRequest userLeaveRequest);

	Boolean getLeaveService(String providerName);
}
