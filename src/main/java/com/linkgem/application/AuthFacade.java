package com.linkgem.application;

import org.springframework.stereotype.Component;

import com.linkgem.domain.auth.MailAuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthFacade {

	private final MailAuthService mailAuthService;


}
