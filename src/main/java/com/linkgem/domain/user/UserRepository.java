package com.linkgem.domain.user;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByOauthId(String OauthId);

  Optional<User> findByEmail(String Email);
}
