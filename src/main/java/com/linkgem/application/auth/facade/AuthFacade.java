package com.linkgem.application.auth.facade;

import com.linkgem.application.auth.service.MailAuthService;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthFacade {

	private final MailAuthService mailAuthService;


}
