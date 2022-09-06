package com.linkgem.domain.user;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMailAuthServiceImpl implements UserMailAuthService{

	private final JavaMailSender javaMailSender;
	private final UserReader userReader;

	@Override
	@Async
	public void mailSend(String emailAddress, Long userId) {
		User user = userReader.find(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(emailAddress);
		smm.setSubject("링크잼 이메일 인증");
		//smm.setText();

		javaMailSender.send(smm);
	}

}
