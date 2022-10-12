package com.linkgem.infrastructure.auth;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.auth.Auth;
import com.linkgem.domain.auth.AuthStore;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthStoreImpl implements AuthStore {

	private final AuthRepository authRepository;
	@Override
	public Auth create(Auth auth) {
		return authRepository.save(auth);
	}
}
