package com.linkgem.domain.common.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class File {

    private String url;

    public static File empty() {
        return new File("");
    }
}
