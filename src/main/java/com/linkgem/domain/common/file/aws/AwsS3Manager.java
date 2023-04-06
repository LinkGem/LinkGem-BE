package com.linkgem.domain.common.file.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.linkgem.domain.common.file.FileCommand;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AwsS3Manager {

    final private String accessKey;

    final private String secretKey;

    final private String domain;

    final private String bucket;

    final private String region;

    final private AmazonS3 s3Client;

    public AwsS3Manager(
        @Value("${aws.credentials.accessKey}") String accessKey,
        @Value("${aws.credentials.secretKey}") String secretKey,
        @Value("${aws.s3.domain}") String domain,
        @Value("${aws.s3.bucket}") String bucket,
        @Value("${aws.s3.region}") String region
    ) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.domain = domain;
        this.bucket = bucket;
        this.region = region;

        AWSCredentials credentials = new BasicAWSCredentials(
            accessKey,
            secretKey
        );

        this.s3Client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.valueOf(region))
            .build();

        log.info("[CONFIG] AWS client create Completed");
    }

    public String upload(S3FileCommand.Upload uploadCommand) {

        PutObjectRequest putObjectRequest = createPutObject(uploadCommand);
        s3Client.putObject(putObjectRequest);
        log.info("[AWS] file upload success to s3 : {}", putObjectRequest.getKey());

        return String.format("%s/%s", domain, putObjectRequest.getKey());
    }

    public void delete(FileCommand.DeleteFile deleteCommand) {

        final String objectKey = deleteCommand.getUrl()
            .replace(String.format("%s/", this.domain), "");

        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucket, objectKey);

        this.s3Client.deleteObject(deleteObjectRequest);
        log.info("[AWS] delete files in s3 : {}", objectKey);
    }

    private PutObjectRequest createPutObject(S3FileCommand.Upload uploadCommand) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(uploadCommand.getContentType());

        if (uploadCommand.getContentLength() > 0) {
            objectMetadata.setContentLength(uploadCommand.getContentLength());
        }

        return new PutObjectRequest(
            this.bucket,
            uploadCommand.getObjectKey(),
            uploadCommand.getInputStream(),
            objectMetadata
        );
    }
}
