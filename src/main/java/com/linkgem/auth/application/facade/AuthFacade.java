package com.linkgem.auth.application.facade;

import com.linkgem.auth.application.service.MailAuthService;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthFacade {

	private final MailAuthService mailAuthService;


}
