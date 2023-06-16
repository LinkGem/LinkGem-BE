package com.linkgem.infrastructure.user;

import com.linkgem.domain.user.User;

import java.util.Optional;

public interface UserReader {

	Optional<User> find(Long id);
	Optional<User> findByLoginEmail(String loginEmail);
	boolean existsByNickname(String nickName);
	Optional<User> findByNickname(String nickName);

    User get(Long id);
}
