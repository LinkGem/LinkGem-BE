package com.linkgem.infrastructure.link;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.link.Link;
import com.linkgem.domain.link.LinkStore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class LinkStoreImpl implements LinkStore {

    private final LinkRepository repository;

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
