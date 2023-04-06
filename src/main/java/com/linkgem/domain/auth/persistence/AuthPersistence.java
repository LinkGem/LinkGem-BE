package com.linkgem.domain.auth.persistence;

import com.linkgem.domain.auth.domain.AuthType;
import com.linkgem.domain.auth.domain.Auth;

import java.util.Optional;

public interface AuthPersistence {

	Optional<Auth> find(Long id);

	Optional<Auth> findByUserIdAndAuthType(Long userId, AuthType authType);

	Auth create(Auth auth);
}
