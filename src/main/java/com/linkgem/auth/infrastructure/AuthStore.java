package com.linkgem.auth.infrastructure;

import com.linkgem.domain.auth.Auth;

public interface AuthStore {

	Auth create(Auth auth);
}
