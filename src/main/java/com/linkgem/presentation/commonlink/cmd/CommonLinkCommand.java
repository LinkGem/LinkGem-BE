package com.linkgem.presentation.commonlink.cmd;

import com.linkgem.domain.commonlink.CommonLink;
import com.linkgem.domain.link.opengraph.OpenGraph;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonLinkCommand {

    @Getter
    public static class Create {
        private String url;

        private Create(String url) {
            this.url = url;
        }

        public static Create of(String url) {
            return new Create(url);
        }

        public CommonLink toEntity(OpenGraph openGraph) {
            return CommonLink.builder()
                .url(this.url)
                .openGraph(openGraph)
                .build();
        }
    }

    @AllArgsConstructor
    @Getter
    public static class DeleteOne {
        private Long id;
    }
}
