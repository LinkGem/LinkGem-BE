package com.linkgem.domain.user;

import static com.linkgem.presentation.user.dto.UserRequest.AddDetailInfoRequest;

public interface UserService {

  void addDetailInfo(Long userId, AddDetailInfoRequest addDetailInfoRequest);


}
