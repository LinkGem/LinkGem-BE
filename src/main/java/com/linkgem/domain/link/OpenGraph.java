package com.linkgem.domain.link;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class OpenGraph {

    @Column(length = 1000)
    private String description;

    private String title;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;
}
