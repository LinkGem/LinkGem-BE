package com.linkgem.domain.oauth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.linkgem.infrastructure.user.UserRepository;

@ExtendWith(MockitoExtension.class)
class OauthServiceTest {

	@InjectMocks
	private OauthServiceImpl oauthService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private InMemoryProviderRepository inMemoryProviderRepository;

	@Mock
	private TokenProvider tokenProvider;





	@Test
	void login() {

	}

	@Test
	void reissue() {
	}
}