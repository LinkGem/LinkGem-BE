package com.linkgem.presentation.user;

import com.linkgem.domain.auth.MailAuthService;
import com.linkgem.domain.user.UserReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements ApplicationRunner {

	@Autowired
	MailAuthService mailAuthService;
	@Autowired
	UserReader userReader;


	@Override
	public void run(ApplicationArguments args) throws Exception {
		mailAuthService.mailSend("hose0728@naver.com",6L);
//		boolean result = mailAuthService.mailConfirm("hose0728@naver.com", 6L);
//		AuthInfo.MailConfirm mailConfirm = AuthInfo.MailConfirm.of(result);
//		AuthResponse.MailConfirmResponse mailConfirmResponse = AuthResponse.MailConfirmResponse.of(mailConfirm);
//		System.out.println("mailConfirmResponse = " + mailConfirmResponse);
	}
}
