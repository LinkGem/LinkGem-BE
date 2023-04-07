package com.linkgem.domain.gem.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.linkgem.domain.common.BaseEntity;
import com.linkgem.domain.gem.opengraph.OpenGraph;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
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

    @Builder
    private CommonLink(String url, OpenGraph openGraph) {
        Objects.requireNonNull(url, "url is required");
        this.url = url;
        this.openGraph = openGraph;
    }
}
