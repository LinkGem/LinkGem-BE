package com.linkgem.domain.user;

import java.util.Optional;

public interface UserReader {

    Optional<User> find(Long id);

    User get(Long id);
}
