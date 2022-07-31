package com.linkgem.infrastructure.common.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.linkgem.domain.common.file.Directory;

@Component
public class S3ObjectKeyCreator {

    private final String profile;

    private final String IMAGE_DIRECTORY = "image";

    public S3ObjectKeyCreator(
        @Value("${spring.profiles.active}") String profile) {
        this.profile = profile;
    }

    public String create(
        Directory directory,
        Long userId,
        Long id
    ) {
        return new StringBuilder()
            .append(profile)
            .append("/")
            .append(IMAGE_DIRECTORY)
            .append("/")
            .append(directory.name())
            .append("/")
            .append(userId)
            .append("/")
            .append(directory.name()).append("_").append(id)
            .toString();
    }
}
