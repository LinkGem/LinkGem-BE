package com.linkgem.domain.common.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FileInfo {

    private String url;

    public static FileInfo empty() {
        return new FileInfo("");
    }
}
