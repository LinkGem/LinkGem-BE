package com.linkgem.domain.link;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class OpenGraph {

    @Column(length = 1000)
    private String description;

    private String title;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Builder
    private OpenGraph(String description, String title, String imageUrl) {
        this.description = description;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public static OpenGraph createEmpty() {
        return new OpenGraph("", "", "");
    }

}
