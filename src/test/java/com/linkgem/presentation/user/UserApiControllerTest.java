package com.linkgem.presentation.user;

import static com.linkgem.presentation.user.dto.UserRequest.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkgem.domain.user.UserServiceImpl;
import com.linkgem.infrastructure.config.Interceptor.JwtTokenInterceptor;

@WebMvcTest(UserApiController.class)
class UserApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtTokenInterceptor jwtTokenInterceptor;
	@MockBean
	UserServiceImpl userService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("addDetailInfo API 테스트")
	void addDetailInfo() throws Exception {

		given(jwtTokenInterceptor.preHandle(any(), any(), any())).willReturn(true);
		doNothing().when(userService).addDetailInfo(any(), any());
		AddDetailInfoRequest addDetailInfoRequest = AddDetailInfoRequest.builder()
			.userNickname("test")
			.careerYear(10)
			.jobName("테스터")
			.build();

		String jsonRequest = objectMapper.writeValueAsString(addDetailInfoRequest);

		mockMvc.perform(patch("/api/v1/user/addDetailInfo").contentType(MediaType.APPLICATION_JSON)
			.requestAttr(JwtTokenInterceptor.USER_INFORMATION_NAME, 1L)
			.content(jsonRequest)).andDo(print());
	}

	@Test
	@DisplayName("회원탈퇴 API 테스트")
	void leave() throws Exception {

		given(jwtTokenInterceptor.preHandle(any(), any(), any())).willReturn(true);
		doNothing().when(userService).leave(any());

		String jsonRequest = objectMapper.writeValueAsString(null);

		mockMvc.perform(patch("/api/v1/user/addDetailInfo")
			.contentType(MediaType.APPLICATION_JSON)
			.requestAttr(JwtTokenInterceptor.USER_INFORMATION_NAME, 1L)
			.content(jsonRequest)
		).andDo(print());

	}
}