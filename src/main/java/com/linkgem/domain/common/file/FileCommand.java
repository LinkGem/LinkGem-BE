package com.linkgem.domain.common.file;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class FileCommand {

    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    @Getter
    public static class UploadFile {
        private MultipartFile file;
        private Directory directory;
        private Long userId;
        private Long id;

        public static UploadFile of(MultipartFile file, Directory directory, Long userId, Long id) {
            return new UploadFile(file, directory, userId, id);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    @Getter
    public static class UploadUrlFile {
        private String url;
        private Directory directory;
        private Long userId;
        private Long id;

        public static UploadUrlFile of(String url, Directory directory, Long userId, Long id) {
            return new UploadUrlFile(url, directory, userId, id);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class DeleteFile {
        private String url;
    }
}
