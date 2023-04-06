package com.linkgem.domain.auth.persistence;

import com.linkgem.domain.auth.domain.AuthType;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.auth.domain.Auth;

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