package com.linkgem.infrastructure.common.aws;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.linkgem.domain.common.file.Directory;

class S3ObjectKeyCreatorTest {

    @Test
    void createS3ObjectKey() {
        S3ObjectKeyCreator s3ObjectKeyCreator = new S3ObjectKeyCreator("image", "s3domain");
        String url = s3ObjectKeyCreator.create(Directory.LINK, 1L, 1L);

        Assertions.assertNotNull(url);
    }
}
