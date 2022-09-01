package com.linkgem.infrastructure.common.aws;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.linkgem.domain.common.file.Directory;

@Component
public class S3ObjectKeyCreator {

    private final String profile;
    @Value("${aws.s3.domain}")
    private String s3domain;

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

    public String createDefaultImageUrl(){
        Random random = new Random();
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
