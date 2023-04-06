package com.linkgem.domain.common.file;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class FileCommand {
    private FileCommand() {
    }

    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    @Getter
    public static class UploadFile {
        private MultipartFile file;
        private String objectKey;

        public static UploadFile of(MultipartFile file, String objectKey) {
            return new UploadFile(file, objectKey);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    @Getter
    public static class UploadUrlFile {
        private String url;
        private String objectKey;
        private String domain;

        public static UploadUrlFile of(String url, String objectKey, String domain) {
            return new UploadUrlFile(url, objectKey, domain);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class DeleteFile {
        private String url;

        public static DeleteFile of(String url) {
            return new DeleteFile(url);
        }
    }
}
