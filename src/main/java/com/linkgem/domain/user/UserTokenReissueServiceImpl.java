package com.linkgem.domain.user;

import org.springframework.stereotype.Service;

import com.linkgem.domain.user.provider.TokenProvider;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import com.linkgem.presentation.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserTokenReissueServiceImpl implements UserTokenReissueService{

	private final TokenProvider tokenProvider;
	@Override
	public UserResponse.TokenReissueResponse reissue(String accessToken, String refreshToken) {
		if (tokenProvider.isExpiredAccessToken(accessToken)) {
			String userId = tokenProvider.isValidRefreshToken(refreshToken);
			String createdAccessToken = tokenProvider.createAccessToken(userId);
			return UserResponse.TokenReissueResponse.builder()
				.accessToken(createdAccessToken)
				.build();
		} else
			throw new BusinessException(ErrorCode.ACCESS_TOKEN_NOT_EXPIRED);
	}
}
