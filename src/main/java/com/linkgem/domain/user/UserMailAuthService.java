package com.linkgem.domain.user;

public interface UserMailAuthService {

	void mailSend(String emailAddress, Long userId);
}
