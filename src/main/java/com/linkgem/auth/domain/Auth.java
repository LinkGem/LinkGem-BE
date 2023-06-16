package com.linkgem.auth.domain;

import com.linkgem.domain.auth.AuthType;
import com.linkgem.domain.common.BaseEntity;
import com.linkgem.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	private String certificationCode;

	private AuthType authType;

	private LocalDateTime expiredDate;
	@ColumnDefault("0")
	@Column(nullable = false)
	private boolean auth;

	public void authenticate(){
		this.auth = true;
	}


	@Builder
	public Auth(User user, AuthType authType, LocalDateTime expiredDate, String certificationCode){
		this.auth = false;
		this.authType = authType;
		this.user = user;
		this.expiredDate = expiredDate;
		this.certificationCode = certificationCode;

	}
	public void changeMailAuth(LocalDateTime expired_date, String certification_code){
		this.expiredDate = expired_date;
		this.certificationCode = certification_code;
		this.auth = false;
	}
}
