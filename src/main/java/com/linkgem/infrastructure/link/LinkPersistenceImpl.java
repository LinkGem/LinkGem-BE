package com.linkgem.infrastructure.link;

import java.util.List;
import java.util.Optional;

import com.linkgem.domain.link.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class LinkPersistenceImpl implements LinkPersistence {

    private final LinkRepository repository;

    @Override
    public Page<LinkInfo.Search> findAll(LinkCommand.SearchLinks searchLinks, Pageable pageable) {
        return repository.findAll(searchLinks, pageable);
    }

    @Override
    public List<Link> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public List<Link> findAllByGemBoxId(Long gemBoxId) {
        return repository.findAllByGemBoxId(gemBoxId);
    }

    @Override
    public Optional<Link> find(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId);
    }

    @Override
    public Link get(Long id, Long userId) {
        return this.find(id, userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.LINK_NOT_FOUND));
    }

    @Override
    public Optional<Link> findOneJoinUser(Long id, Long userId) {
        return repository.findOneJoinUser(id, userId);
    }

    @Override
    public Link create(Link link) {
        return repository.save(link);
    }

    @Override
    public Long delete(Link link) {
        repository.delete(link);
        return link.getId();
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        repository.deleteAllByUserId(userId);
    }
}
