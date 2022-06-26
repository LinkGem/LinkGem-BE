package com.linkgem.domain.User;

import com.linkgem.domain.job.Job;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String nickName;
  @ManyToOne
  @JoinColumn(name = "JOB_ID")
  private Job job;

  @Builder
  public User(String email, String nickName) {
    this.email = email;
    this.nickName = nickName;
  }

}
