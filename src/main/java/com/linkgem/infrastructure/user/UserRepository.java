package com.linkgem.infrastructure.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkgem.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLoginEmail(String loginEmail);

  boolean existsByNickname(String nickName);
}
