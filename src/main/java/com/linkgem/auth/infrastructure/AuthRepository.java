package com.linkgem.auth.infrastructure;

import com.linkgem.domain.auth.Auth;
import com.linkgem.domain.auth.AuthType;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthRepository extends JpaRepository<Auth,Long>,AuthRepositoryCustom {
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("select a from Auth a where a.user.id = :userId and a.authType = :authType")
	Optional<Auth> findByUserIdAndAuthType(@Param("userId") Long userId,@Param("authType") AuthType authType);
}
