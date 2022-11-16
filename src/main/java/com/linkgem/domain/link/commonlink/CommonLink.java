package com.linkgem.domain.link.commonlink;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.linkgem.domain.common.BaseEntity;
import com.linkgem.domain.link.opengraph.OpenGraph;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CommonLink extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Embedded
    private OpenGraph openGraph;
}
