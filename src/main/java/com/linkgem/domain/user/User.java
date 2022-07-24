package com.linkgem.domain.user;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.linkgem.domain.common.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
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


  @Builder
  public User(String loginEmail, String nickname, String oauthId,String name) {
    this.loginEmail = loginEmail;
    this.nickname = nickname;
    this.oauthId = oauthId;
    this.userPhase = UserPhase.READY;
    this.name = name;
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

  public void updateUserPhaseRegistered(){
    this.userPhase = UserPhase.REGISTERED;
  }

}
