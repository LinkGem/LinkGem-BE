package com.linkgem.infrastructure.auth;

import com.linkgem.domain.auth.Auth;
import com.linkgem.domain.auth.AuthType;

import java.util.Optional;

public interface AuthReader {

	Optional<Auth> find(Long id);

	Optional<Auth> findByUserIdAndAuthType(Long userId, AuthType authType);

}
