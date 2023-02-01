package com.linkgem.domain.user;

import com.linkgem.domain.common.BaseEntity;
import com.linkgem.domain.user.convertor.UserPhaseConvertor;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String loginEmail;

	private String mailEmail;
	private String name;
	private String nickname;
	private String job;
	@Column(name = "oauth_id", nullable = false)
	private String oauthId;
	@Convert(converter = UserPhaseConvertor.class)
	private UserPhase userPhase;
	@Column(name = "career_year")
	private int careerYear;
	@Column(name = "profile_image_url")
	private String profileImageUrl;

	@Column(name = "is_saved_first_link", nullable = false)
	private Boolean isSavedFirstLink;

	@Builder
	public User(String loginEmail, String nickname, String oauthId, String name) {
		this.loginEmail = loginEmail;
		this.nickname = nickname;
		this.oauthId = oauthId;
		this.userPhase = UserPhase.READY;
		this.name = name;
		this.isSavedFirstLink = false;
	}

	public void saveFirstLink() {
		this.isSavedFirstLink = true;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateCareerYear(int careerYear) {
		this.careerYear = careerYear;
	}

	public void updateJob(String job) {
		this.job = job;
	}

	public void updateUserPhaseRegistered() {
		this.userPhase = UserPhase.REGISTERED;
	}

	public void updateProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public void updateUserPhaseReady() {
		this.userPhase = UserPhase.READY;
	}

	public void leave() {
		this.userPhase = UserPhase.DELETED;
		this.job = null;
		this.nickname = null;
		this.careerYear = 0;
		this.mailEmail = null;
		this.profileImageUrl = null;
		this.isSavedFirstLink = false;

	}

	public void saveMailEmail(String mailEmail){
		this.mailEmail = mailEmail;
	}




}
