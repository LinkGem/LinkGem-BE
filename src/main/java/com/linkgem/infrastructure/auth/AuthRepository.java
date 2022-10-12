package com.linkgem.infrastructure.auth;

import com.linkgem.domain.auth.AuthType;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkgem.domain.auth.Auth;

public interface AuthRepository extends JpaRepository<Auth,Long>,AuthRepositoryCustom {

	Optional<Auth> findByUserId(Long userId);
	Optional<Auth> findByUserIdAndAuthType(Long userId, AuthType authType);
}
