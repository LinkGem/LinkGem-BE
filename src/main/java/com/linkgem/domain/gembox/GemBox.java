package com.linkgem.domain.gembox;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.linkgem.domain.common.BaseEntity;

import lombok.Getter;

@Getter
@Table(name = "gem_box")
@Entity
public class GemBox extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
