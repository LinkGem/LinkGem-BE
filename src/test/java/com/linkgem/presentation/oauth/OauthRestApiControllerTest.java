package com.linkgem.presentation.oauth;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.linkgem.domain.oauth.OauthService;
import com.linkgem.domain.oauth.TokenProvider;
import com.linkgem.domain.user.UserPhase;
import com.linkgem.presentation.oauth.dto.OauthResponse.LoginResponse;
import com.linkgem.presentation.oauth.dto.OauthResponse.TokenReissueResponse;

@WebMvcTest(OauthRestApiController.class)
class OauthRestApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OauthService oauthService;

	@MockBean
	private TokenProvider tokenProvider;

	@Test
	@DisplayName("로그인 api 테스트")
	void login() throws Exception {
		given(oauthService.login(any(), any())).willReturn(LoginResponse.builder()
			.id(1L)
			.name("테스트")
			.mailEmail(null)
			.userPhase(UserPhase.READY)
			.accessToken("ACCESSTOKEN")
			.refreshToken("REFRESHTOKEN")
			.nickname(null)
			.loginEmail("test@test.com")
			.build());

		mockMvc.perform(get("/api/v1/oauth/login/naver?code=" + "test"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result").exists())
			.andExpect(jsonPath("$.timestamp").exists())
			.andExpect(jsonPath("$.result.id").value(1L))
			.andExpect(jsonPath("$.result.loginEmail").value("test@test.com"))
			.andExpect(jsonPath("$.result.name").value("테스트"))
			.andExpect(jsonPath("$.result.accessToken").value("ACCESSTOKEN"))
			.andExpect(jsonPath("$.result.refreshToken").value("REFRESHTOKEN"))
			.andExpect(jsonPath("$.result.userPhase").value("READY"))
			.andExpect(jsonPath("$.result.mailEmail").doesNotExist())
			.andExpect(jsonPath("$.result.nickname").doesNotExist())
			.andDo(print());

		verify(oauthService).login(any(), any());
	}

	@Test
	@DisplayName("엑세스 토큰 재발급 테스트")
	void reissue() throws Exception {

		given(oauthService.reissue(any(), any())).willReturn(TokenReissueResponse.builder()
			.accessToken("test")
			.build());

		mockMvc.perform(post("/api/v1/oauth/reissue")
			.header("ACCESS-TOKEN","TEST")
			.header("REFRESH-TOKEN","TEST"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result").exists())
			.andExpect(jsonPath("$.timestamp").exists())
			.andExpect(jsonPath("$.result.accessToken").value("test"))
			.andDo(print());

		verify(oauthService).reissue(any(),any());



	}
}