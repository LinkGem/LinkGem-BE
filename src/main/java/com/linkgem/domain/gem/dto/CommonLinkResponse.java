package com.linkgem.domain.gem.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

public class CommonLinkResponse {
    private CommonLinkResponse() {
    }

    @ApiModel(description = "잼크루 픽 목록 조회 응답")
    @Getter
    public static class Main {

        private Long id;
        private String url;

        private String title;
        private String description;
        private String imageUrl;
        private String siteName;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        private Main(CommonLinkInfo.Main commonLinkInfo) {
            this.id = commonLinkInfo.getId();
            this.url = commonLinkInfo.getUrl();
            this.title = commonLinkInfo.getOpenGraphInfo().getTitle();
            this.description = commonLinkInfo.getOpenGraphInfo().getDescription();
            this.imageUrl = commonLinkInfo.getOpenGraphInfo().getImageUrl();
            this.siteName = commonLinkInfo.getOpenGraphInfo().getSiteName();
            this.createDate = commonLinkInfo.getCreateDate();
            this.updateDate = commonLinkInfo.getUpdateDate();
        }

        public static Main of(CommonLinkInfo.Main commonLinkInfo) {
            return new Main(commonLinkInfo);
        }

        public static List<Main> ofs(List<CommonLinkInfo.Main> commonLinkInfos) {
            return commonLinkInfos.stream()
                .map(Main::of)
                .collect(Collectors.toList());
        }
    }

}
