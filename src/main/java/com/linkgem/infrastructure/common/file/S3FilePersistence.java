package com.linkgem.infrastructure.common.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.linkgem.domain.common.file.FileCommand;
import com.linkgem.domain.common.file.File;
import com.linkgem.domain.common.file.FilePersistence;
import com.linkgem.infrastructure.common.aws.AwsS3Manager;
import com.linkgem.infrastructure.common.aws.S3FileCommand;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class S3FilePersistence implements FilePersistence {

    private final AwsS3Manager awsS3Manager;

    @Override
    public File store(FileCommand.UploadFile uploadCommand) {
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
                uploadCommand.getObjectKey()
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.S3_FILE_UPLOAD_FAIL);
        }

    }

    @Override
    public File store(FileCommand.UploadUrlFile uploadCommand) {

        String urlString = uploadCommand.getUrl();

        // brunch에 경우 이미지 url이 "//" 로 시작한다
        if (urlString.startsWith("//")) {
            urlString = "https:" + urlString;
        } else if (urlString.startsWith("/")) {
            urlString = "https://" + uploadCommand.getDomain() + urlString;
        }

        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();

            final InputStream inputStream = urlConnection.getInputStream();
            final String contentType = urlConnection.getContentType();
            final long contentLength = urlConnection.getContentLength();

            return storeToS3(
                inputStream,
                contentType,
                contentLength,
                uploadCommand.getObjectKey()
            );

        } catch (MalformedURLException e) {
            return File.empty();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.S3_FILE_UPLOAD_FAIL);
        }
    }

    @Override
    public void delete(FileCommand.DeleteFile deleteCommand) {
        awsS3Manager.delete(deleteCommand);
    }

    private File storeToS3(
        InputStream inputStream,
        String contentType,
        Long contentLength,
        String objectKey
    ) {

        S3FileCommand.Upload s3FileCommand = S3FileCommand.Upload.builder()
            .inputStream(inputStream)
            .contentType(contentType)
            .contentLength(contentLength)
            .objectKey(objectKey)
            .build();

        String fileUrl = awsS3Manager.upload(s3FileCommand);

        return new File(fileUrl);
    }
}
