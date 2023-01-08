package com.linkgem.domain.user.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "oauth")
public class OauthProvider {

	private final Map<String, Provider> provider = new HashMap<>();

	@Getter
	@ConstructorBinding
	@RequiredArgsConstructor
	public static class Provider {

		private final String clientId;
		private final String clientSecret;
		private final String redirectUrl;
		private final String tokenUrl;
		private final String userInfoUrl;
		private final String leaveUrl;


	}

	public Provider getProvider(String providerName){
		Provider provider = this.provider.get(providerName);
		if (Objects.isNull(provider)){
			throw new BusinessException(ErrorCode.PROVIDER_NOT_VALID);
		}
		return provider;
	}

}
