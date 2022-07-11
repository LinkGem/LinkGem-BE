package com.linkgem.infrastructure.user;


import com.linkgem.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByOauthId(String OauthId);

  Optional<User> findByEmail(String Email);
}
