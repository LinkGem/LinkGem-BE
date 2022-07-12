package com.linkgem.domain.user;

import com.linkgem.domain.common.BaseEntity;
import com.linkgem.domain.job.Job;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private String email;
    @Column(nullable = false)
    private String nickName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;
    @Column(name = "oauth_id", nullable = false)
    private String oauthId;
    private UserPhase userPhase;




    @Builder
    public User(String email, String nickName, String oauthId) {
        this.email = email;
        this.nickName = nickName;
        this.oauthId = oauthId;
    }

}
