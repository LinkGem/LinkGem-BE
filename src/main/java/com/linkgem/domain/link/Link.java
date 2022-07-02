package com.linkgem.domain.link;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.linkgem.domain.common.BaseEntity;
import com.linkgem.domain.gembox.GemBox;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Embedded
    private OpenGraph openGraph;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gem_box_id")
    private GemBox gemBox;

    @Builder
    public Link(String url, String memo, OpenGraph openGraph, Long userId) {
        this.url = url;
        this.memo = memo;
        this.openGraph = openGraph;
        this.userId = userId;
    }

    public void setGemBox(GemBox gemBox) {
        this.gemBox = gemBox;
    }
}
