package com.linkgem.domain.gem.opengraph;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OpenGraphInfo {
    private String title;
    private String description;
    private String imageUrl;
    private String siteName;

    public static OpenGraphInfo of(OpenGraph openGraph) {
        return OpenGraphInfo.builder()
            .imageUrl(openGraph.getImageUrl())
            .title(openGraph.getTitle())
            .description(openGraph.getDescription())
            .siteName(openGraph.getSiteName())
            .build();
    }
}
