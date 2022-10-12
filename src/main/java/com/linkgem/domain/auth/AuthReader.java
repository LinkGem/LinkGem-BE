package com.linkgem.domain.auth;

import java.util.Optional;

public interface AuthReader {

	Optional<Auth> find(Long id);

	Optional<Auth> findByUserIdAndAuthType(Long userId,AuthType authType);

}
