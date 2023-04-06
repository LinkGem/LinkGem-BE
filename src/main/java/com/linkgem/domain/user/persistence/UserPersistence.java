package com.linkgem.domain.user.persistence;

import com.linkgem.domain.user.domain.User;

import java.util.Optional;

public interface UserPersistence {

	Optional<User> find(Long id);
	Optional<User> findByLoginEmail(String loginEmail);
	boolean existsByNickname(String nickName);
	Optional<User> findByNickname(String nickName);

    User get(Long id);

	User create(User user);
}
