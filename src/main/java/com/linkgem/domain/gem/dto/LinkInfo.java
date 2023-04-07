package com.linkgem.domain.gem.dto;

import java.time.LocalDateTime;

import com.linkgem.domain.gem.domain.Link;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LinkInfo {
    private LinkInfo() {
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Main {

        private Long id;
        private String memo;
        private String url;

        private String title;
        private String description;
        private String imageUrl;
        private String siteName;
        private boolean isFavorites;

        private Long gemBoxId;

        private Long userId;
        private String userNickname;

        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        public Main(Link link) {
            this.id = link.getId();
            this.memo = link.getMemo();
            this.url = link.getUrl();
            this.title = link.getOpenGraph().getTitle();
            this.description = link.getOpenGraph().getDescription();
            this.imageUrl = link.getOpenGraph().getImageUrl();
            this.siteName = link.getOpenGraph().getSiteName();
            this.isFavorites = link.isFavorites();
            this.gemBoxId = link.getGemBox() == null ? null : link.getGemBox().getId();
            this.userId = link.getUser().getId();
            this.userNickname = link.getUser().getNickname();
            this.createDate = link.getCreateDate();
            this.updateDate = link.getUpdateDate();
        }

        public static Main of(Link link) {
            return new Main(link);
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Detail extends Main {

        public Detail(Link link) {
            super(link);
        }

        public static Detail of(Link link) {
            return new Detail(link);
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Search extends Main {

        public Search(Link link) {
            super(link);
        }

        public static Search of(Link link) {
            return new Search(link);
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Create extends Main {

        public Create(Link link) {
            super(link);
        }

        public static Create of(Link link) {
            return new Create(link);
        }
    }
}
