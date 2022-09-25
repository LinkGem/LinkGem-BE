package com.linkgem.domain.user;

import java.util.Optional;

public interface UserReader {

	Optional<User> find(Long id);
	Optional<User> findByLoginEmail(String loginEmail);
	boolean existsByNickname(String nickName);
	Optional<User> findByNickname(String nickName);

    User get(Long id);
}
