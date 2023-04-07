package com.linkgem.domain.gem.dto;

import java.time.LocalDateTime;

import com.linkgem.domain.gem.domain.CommonLink;
import com.linkgem.domain.gem.opengraph.OpenGraphInfo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonLinkInfo {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Main {
        private Long id;

        private String url;
        private OpenGraphInfo openGraphInfo;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        public Main(CommonLink commonLink) {
            this.id = commonLink.getId();
            this.url = commonLink.getUrl();
            this.openGraphInfo = OpenGraphInfo.of(commonLink.getOpenGraph());
            this.createDate = commonLink.getCreateDate();
            this.updateDate = commonLink.getUpdateDate();
        }

        public static CommonLinkInfo.Main of(CommonLink commonLink) {

            return new CommonLinkInfo.Main(commonLink);
        }

    }
}
