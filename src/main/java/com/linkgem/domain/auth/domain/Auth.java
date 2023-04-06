package com.linkgem.domain.auth.domain;

import com.linkgem.domain.common.BaseEntity;
import com.linkgem.domain.user.domain.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
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
