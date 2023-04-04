package com.linkgem.domain.auth;

import java.util.Optional;

public interface AuthPersistence {

	Optional<Auth> find(Long id);

	Optional<Auth> findByUserIdAndAuthType(Long userId,AuthType authType);

	Auth create(Auth auth);
}
