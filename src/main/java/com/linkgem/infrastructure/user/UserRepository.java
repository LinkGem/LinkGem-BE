package com.linkgem.infrastructure.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkgem.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
