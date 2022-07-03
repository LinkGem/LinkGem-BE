package com.linkgem.infrastructure.gembox;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.gembox.GemBox;
import com.linkgem.domain.gembox.GemBoxReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class GemBoxReaderImpl implements GemBoxReader {

    private final GemBoxRepository repository;

    @Override
    public Optional<GemBox> find(String name, Long userId) {
        return repository.findByNameAndUserId(name, userId);
    }

    @Override
    public Optional<GemBox> find(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<GemBox> find(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId);
    }

    @Override
    public List<GemBox> findAll(Long userId) {
        return repository.findAllByUserId(userId);
    }
}
