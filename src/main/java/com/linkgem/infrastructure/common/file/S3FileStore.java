package com.linkgem.infrastructure.common.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.linkgem.domain.common.file.Directory;
import com.linkgem.domain.common.file.FileCommand;
import com.linkgem.domain.common.file.FileInfo;
import com.linkgem.domain.common.file.FileStore;
import com.linkgem.infrastructure.common.aws.AwsS3Manager;
import com.linkgem.infrastructure.common.aws.S3FileCommand;
import com.linkgem.infrastructure.common.aws.S3ObjectKeyCreator;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class S3FileStore implements FileStore {

    private final AwsS3Manager awsS3Manager;
    private final S3ObjectKeyCreator s3ObjectKeyCreator;

    @Override
    public FileInfo store(FileCommand.UploadFile uploadCommand) {
        try {
            MultipartFile file = uploadCommand.getFile();

            if (file == null || file.isEmpty()) {
                throw new BusinessException(ErrorCode.FILE_EMPTY);
            }

            final InputStream inputStream = file.getInputStream();
            final String contentType = file.getContentType();
            final Long contentLength = file.getSize();

            return storeToS3(
                inputStream,
                contentType,
                contentLength,
                uploadCommand.getDirectory(),
                uploadCommand.getUserId(),
                uploadCommand.getId()
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.S3_FILE_UPLOAD_FAIL);
        }

    }

    @Override
    public FileInfo store(FileCommand.UploadUrlFile uploadCommand) {
        try {
            URL url = new URL(uploadCommand.getUrl());
            URLConnection urlConnection = url.openConnection();

            final InputStream inputStream = urlConnection.getInputStream();
            final String contentType = urlConnection.getContentType();
            final long contentLength = urlConnection.getContentLength();

            return storeToS3(
                inputStream,
                contentType,
                contentLength,
                uploadCommand.getDirectory(),
                uploadCommand.getUserId(),
                uploadCommand.getId()
            );

        } catch (MalformedURLException e) {
            return FileInfo.empty();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.S3_FILE_UPLOAD_FAIL);
        }
    }

    @Override
    public void delete(FileCommand.DeleteFile deleteCommand) {
        awsS3Manager.delete(deleteCommand);
    }

    private FileInfo storeToS3(
        InputStream inputStream,
        String contentType,
        Long contentLength,
        Directory directory,
        Long userId,
        Long id
    ) {
        final String objectKey = s3ObjectKeyCreator.create(directory, userId, id);

        S3FileCommand.Upload s3FileCommand = S3FileCommand.Upload.builder()
            .inputStream(inputStream)
            .contentType(contentType)
            .contentLength(contentLength)
            .objectKey(objectKey)
            .build();

        String fileUrl = awsS3Manager.upload(s3FileCommand);

        return new FileInfo(fileUrl);
    }
}
