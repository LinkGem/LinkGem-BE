package com.linkgem.domain.link;

import com.linkgem.domain.gembox.GemBox;
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
import java.util.List;
import java.util.stream.Collectors;

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

        if (Boolean.FALSE.equals(user.getIsSavedFirstLink())) {
            user.saveFirstLink();
        }

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

    @Transactional
    @Override
    public List<Link> copyLinks(List<Link> links, GemBox gemBox) {
        List<Link> copiedLinks = links.stream().map(link -> {
            OpenGraph openGraph = openGraphReader.call(link.getUrl());
            return  Link.builder()
                    .memo(link.getMemo())
                    .url(link.getUrl())
                    .user(link.getUser())
                    .openGraph(openGraph)
                    .gemBox(gemBox)
                    .isFavorites(link.isFavorites())
                    .build();
        }).collect(Collectors.toList());

        List<Link> newLinks = linkStore.createAll(copiedLinks);

        // 이전 link 삭제시 openGraph url 도 삭제되므로 openGraph url 를 별도로 생성해야한다.
        newLinks.stream()
                .filter(Link::hasImageUrl)
                .forEach(this::uploadImageUrl);

        return newLinks;
    }

    private void uploadImageUrl(Link createdLink) {

        final String imageUrl = createdLink.getOpenGraph().getImageUrl();
        final String domain = createdLink.getOpenGraph().getDomain();

        final String objectKey =
            s3ObjectKeyCreator.create(Directory.LINK, createdLink.getUser().getId(), createdLink.getId());

        FileCommand.UploadUrlFile uploadCommand =
            FileCommand.UploadUrlFile.of(imageUrl, objectKey, domain);

        FileInfo fileInfo = fileStore.store(uploadCommand);

        createdLink.getOpenGraph().updateImageUrl(fileInfo.getUrl());
    }
}
