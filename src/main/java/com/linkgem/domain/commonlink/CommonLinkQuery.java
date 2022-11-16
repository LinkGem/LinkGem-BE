package com.linkgem.domain.commonlink;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonLinkQuery {

    @AllArgsConstructor
    @Getter
    public static class FindAll {
    }

    @AllArgsConstructor
    @Getter
    public static class FindOne {
        private Long id;
    }

}
