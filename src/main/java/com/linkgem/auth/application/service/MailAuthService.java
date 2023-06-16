package com.linkgem.auth.application.service;

import java.net.URI;

public interface MailAuthService {

	void mailSend(String emailAddress,Long userId);
	 URI mailCheck(Long userId,String randomId);
	 boolean mailConfirm(String emailAddress,Long userId);



}
