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

import org.hibernate.annotations.DynamicUpdate;

import com.linkgem.domain.common.BaseEntity;
import com.linkgem.domain.gembox.GemBox;
import com.linkgem.domain.link.opengraph.OpenGraph;
import com.linkgem.domain.user.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicUpdate
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gem_box_id")
    private GemBox gemBox;

    @Builder
    private Link(String url, String memo, OpenGraph openGraph, User user) {
        validateString(url, "url");
        validateObject(user, "user");
        updateMemo(memo);
        this.url = url;
        this.user = user;

        this.openGraph = openGraph == null ? OpenGraph.createEmpty() : openGraph;
    }

    public void updateMemo(String memo) {
        this.memo = memo == null ? "" : memo;
    }

    public void updateGemBox(GemBox gemBox) {
        this.gemBox = gemBox;
    }
}
