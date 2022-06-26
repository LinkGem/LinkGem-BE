package com.linkgem.domain.link;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.linkgem.domain.common.BaseEntity;

import lombok.Getter;

@Getter
@Entity
public class Link extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    private String memo;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "gem_box_id", nullable = true)
    private Long gemBoxId;

}
