package com.linkgem.domain.link.service;

import com.linkgem.domain.common.file.Directory;
import com.linkgem.domain.common.file.FileCommand;
import com.linkgem.domain.common.file.File;
import com.linkgem.domain.common.file.FilePersistence;
import com.linkgem.domain.gembox.domain.GemBox;
import com.linkgem.domain.gembox.persistence.GemBoxPersistence;
import com.linkgem.domain.link.domain.Link;
import com.linkgem.domain.link.dto.LinkCommand;
import com.linkgem.domain.link.dto.LinkInfo;
import com.linkgem.domain.link.opengraph.OpenGraph;
import com.linkgem.domain.link.opengraph.OpenGraphReader;
import com.linkgem.domain.link.persistence.LinkPersistence;
import com.linkgem.domain.user.domain.User;
import com.linkgem.domain.user.persistence.UserPersistence;
import com.linkgem.domain.common.file.aws.S3ObjectKeyCreator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.linkgem.domain.common.exception.BusinessException;
import com.linkgem.domain.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LinkServiceImpl implements LinkService {

    private final FilePersistence filePersistence;

    private final UserPersistence userPersistence;

    private final OpenGraphReader openGraphReader;

    private final S3ObjectKeyCreator s3ObjectKeyCreator;

    private final GemBoxPersistence gemBoxPersistence;
    private final LinkPersistence linkPersistence;

    @Override
    public Page<LinkInfo.Search> findAll(LinkCommand.SearchLinks searchLinks, Pageable pageable) {
        return linkPersistence.findAll(searchLinks, pageable);
    }

    @Override
    public LinkInfo.Detail findById(Long id, Long userId) {
        Link link = linkPersistence.findOneJoinUser(id, userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.LINK_NOT_FOUND));

        return LinkInfo.Detail.of(link);
    }

    @Transactional
    @Override
    public LinkInfo.Create create(LinkCommand.Create createCommand) {

        User user = userPersistence.find(createCommand.getUserId())
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

        gemBoxPersistence.findDefault(user.getId())
                .ifPresent(link::updateGemBox);

        Link createdLink = linkPersistence.create(link);

        if (link.hasImageUrl()) {
            this.uploadImageUrl(createdLink);
        }

        return LinkInfo.Create.of(createdLink);
    }

    private void uploadImageUrl(Link createdLink) {

        final String imageUrl = createdLink.getOpenGraph().getImageUrl();
        final String domain = createdLink.getOpenGraph().getDomain();

        final String objectKey =
                s3ObjectKeyCreator.create(Directory.LINK, createdLink.getUser().getId(), createdLink.getId());

        FileCommand.UploadUrlFile uploadCommand =
                FileCommand.UploadUrlFile.of(imageUrl, objectKey, domain);

        File file = filePersistence.store(uploadCommand);

        createdLink.getOpenGraph().updateImageUrl(file.getUrl());
    }

    @Transactional
    @Override
    public List<Long> deletes(LinkCommand.Delete command) {

        final Long userId = command.getUserId();

        return command.getIds()
                .stream()
                .map(id -> linkPersistence.find(id, userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::deleteLink)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public List<Long> deleteAllByUserId(Long userId) {
        return linkPersistence.findAllByUserId(userId)
                .stream()
                .map(this::deleteLink)
                .collect(Collectors.toList());

    }

    @Override
    public List<Long> deleteAllByGemBoxId(Long gemBoxId) {
        return linkPersistence.findAllByGemBoxId(gemBoxId)
                .stream()
                .map(this::deleteLink)
                .collect(Collectors.toList());
    }

    private Long deleteLink(Link link) {

        if (link.hasImageUrl()) {
            filePersistence.delete(new FileCommand.DeleteFile(link.getOpenGraph().getImageUrl()));
        }
        return linkPersistence.delete(link);
    }

    @Transactional
    @Override
    public LinkInfo.Main update(LinkCommand.Update updateCommand) {

        final Long userId = updateCommand.getUserId();
        Link findLink = linkPersistence.get(updateCommand.getId(), userId);

        if (updateCommand.getMemo() != null) {
            findLink.updateMemo(updateCommand.getMemo());
        }

        if (Objects.nonNull(updateCommand.getGemBoxId())) {
            this.updateGemBox(findLink, updateCommand.getGemBoxId(), userId);
        }

        if (Objects.nonNull(updateCommand.getIsFavorites())) {
            findLink.updateFavorites(updateCommand.getIsFavorites());
        }

        return LinkInfo.Main.of(findLink);
    }

    private void updateGemBox(Link link, Long gemBoxId, Long userId) {
        GemBox gemBox = gemBoxPersistence.get(gemBoxId, userId);
        link.updateGemBox(gemBox);
    }
}
