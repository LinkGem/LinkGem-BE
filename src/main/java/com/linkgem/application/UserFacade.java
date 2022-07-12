package com.linkgem.application;

import com.linkgem.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {

  private final UserService userService;


}
