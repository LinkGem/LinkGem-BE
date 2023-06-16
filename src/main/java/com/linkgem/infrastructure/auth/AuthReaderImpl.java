package com.linkgem.infrastructure.auth;

import com.linkgem.domain.auth.AuthType;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.auth.Auth;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AuthReaderImpl implements AuthReader {

	private final AuthRepository authRepository;
	@Override
	public Optional<Auth> find(Long id) {
		return authRepository.findById(id);
	}

	@Override
	public Optional<Auth> findByUserIdAndAuthType(Long userId, AuthType authType) {
		return authRepository.findByUserIdAndAuthType(userId,authType);
	}


}
