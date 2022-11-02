package com.linkgem.domain.link;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.domain.common.file.Directory;
import com.linkgem.domain.common.file.FileCommand;
import com.linkgem.domain.common.file.FileInfo;
import com.linkgem.domain.common.file.FileStore;
import com.linkgem.domain.link.opengraph.OpenGraph;
import com.linkgem.domain.link.opengraph.OpenGraphReader;
import com.linkgem.domain.user.User;
import com.linkgem.domain.user.UserReader;
import com.linkgem.infrastructure.common.aws.S3ObjectKeyCreator;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LinkCreateServiceImpl implements LinkCreateService {

    private final FileStore fileStore;

    private final LinkStore linkStore;

    private final UserReader userReader;

    private final OpenGraphReader openGraphReader;

    private final S3ObjectKeyCreator s3ObjectKeyCreator;

    @Transactional
    @Override
    public LinkInfo.Create create(LinkCommand.Create createCommand) {

        User user = userReader.find(createCommand.getUserId())
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        OpenGraph openGraph = openGraphReader.call(createCommand.getUrl());

        Link link = Link.builder()
            .memo(createCommand.getMemo())
            .url(createCommand.getUrl())
            .user(user)
            .openGraph(openGraph)
            .build();

        Link createdLink = linkStore.create(link);

        if (link.hasImageUrl()) {
            this.uploadImageUrl(createdLink);
        }

        return LinkInfo.Create.of(createdLink);
    }

    private void uploadImageUrl(Link createdLink) {

        final String imageUrl = createdLink.getOpenGraph().getImageUrl();

        final String objectKey =
            s3ObjectKeyCreator.create(Directory.LINK, createdLink.getUser().getId(), createdLink.getId());

        FileCommand.UploadUrlFile uploadCommand =
            FileCommand.UploadUrlFile.of(imageUrl, objectKey);

        FileInfo fileInfo = fileStore.store(uploadCommand);

        createdLink.getOpenGraph().updateImageUrl(fileInfo.getUrl());
    }
}
