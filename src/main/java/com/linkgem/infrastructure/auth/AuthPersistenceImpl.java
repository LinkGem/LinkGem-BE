package com.linkgem.infrastructure.auth;

import com.linkgem.domain.auth.AuthType;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.auth.Auth;
import com.linkgem.domain.auth.AuthPersistence;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AuthPersistenceImpl implements AuthPersistence {

	private final AuthRepository authRepository;
	@Override
	public Optional<Auth> find(Long id) {
		return authRepository.findById(id);
	}

	@Override
	public Optional<Auth> findByUserIdAndAuthType(Long userId, AuthType authType) {
		return authRepository.findByUserIdAndAuthType(userId,authType);
	}

	@Override
	public Auth create(Auth auth) {
		return authRepository.saveAndFlush(auth);
	}
}
