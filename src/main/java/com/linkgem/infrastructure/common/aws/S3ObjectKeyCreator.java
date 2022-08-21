package com.linkgem.infrastructure.common.aws;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.linkgem.domain.common.file.Directory;

@Component
public class S3ObjectKeyCreator {

    private final String profile;

    private static final String IMAGE_DIRECTORY = "image";

    public S3ObjectKeyCreator(
        @Value("${spring.profiles.active}") String profile) {
        this.profile = profile;
    }

    public String create(
        Directory directory,
        Long userId,
        Long id
    ) {
        RandomStringUtils.random(12);
        return new StringBuilder()
            .append(profile)
            .append("/")
            .append(IMAGE_DIRECTORY)
            .append("/")
            .append(directory.name())
            .append("/")
            .append(userId)
            .append("/")
            .append(RandomStringUtils.randomAlphabetic(10))
            .append("_")
            .append(directory.name())
            .append("_")
            .append(id)
            .toString();
    }
}
