package com.linkgem.infrastructure.user;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.user.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {

	private final UserRepository userRepository;

	@Override
	public User create(User user) {
		return userRepository.save(user);
	}
}
