package com.linkgem.infrastructure.common.aws;

import java.io.InputStream;

import lombok.Builder;
import lombok.Getter;

public class S3FileCommand {

    @Getter
    public static class Upload {
        private InputStream inputStream;
        private String objectKey;
        private String contentType;
        private long contentLength;

        @Builder
        private Upload(InputStream inputStream, String objectKey, String contentType, long contentLength) {

            this.inputStream = inputStream;
            this.objectKey = objectKey.startsWith("/") ? objectKey.substring(1) : objectKey;
            this.contentType = contentType;
            this.contentLength = contentLength;
        }
    }
}
