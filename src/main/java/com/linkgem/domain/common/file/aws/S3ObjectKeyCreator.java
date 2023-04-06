package com.linkgem.domain.common.file.aws;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.linkgem.domain.common.file.Directory;

@Component
public class S3ObjectKeyCreator {

    private final Random random;

    private final String profile;
    private final String s3domain;

    private static final String IMAGE_DIRECTORY = "image";

    public S3ObjectKeyCreator(
        @Value("${spring.profiles.active}") String profile, @Value("${aws.s3.domain}") String s3domain) {
        this.profile = profile;
        this.s3domain = s3domain;
        this.random = new Random();
    }

    public String create(Directory directory, Long id) {
        return new StringBuilder()
            .append(profile)
            .append("/")
            .append(IMAGE_DIRECTORY)
            .append("/")
            .append(directory.name())
            .append("/")
            .append(RandomStringUtils.randomAlphanumeric(12))
            .append("_")
            .append(directory.name())
            .append("_")
            .append(id)
            .toString();
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
            .append(RandomStringUtils.randomAlphanumeric(12))
            .append("_")
            .append(directory.name())
            .append("_")
            .append(id)
            .toString();
    }

    public String createDefaultImageUrl() {
        String index = Integer.toString(random.nextInt(4));
        return new StringBuilder()
            .append(s3domain)
            .append("/")
            .append(profile)
            .append("/")
            .append(IMAGE_DIRECTORY)
            .append("/")
            .append("DEFAULT_USERPROFILE")
            .append("/")
            .append(index)
            .append(".png")
            .toString();

    }
}
