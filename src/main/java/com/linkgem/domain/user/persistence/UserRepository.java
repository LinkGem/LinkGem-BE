package com.linkgem.domain.user.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkgem.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLoginEmail(String loginEmail);

  boolean existsByNickname(String nickName);

  Optional<User> findByNickname(String nickName);
}
