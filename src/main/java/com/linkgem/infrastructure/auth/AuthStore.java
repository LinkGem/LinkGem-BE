package com.linkgem.infrastructure.auth;

import com.linkgem.domain.auth.Auth;

public interface AuthStore {

	Auth create(Auth auth);
}
