package com.linkgem.domain.gembox.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.linkgem.domain.gembox.domain.GemBox;
import com.linkgem.domain.gembox.dto.GemBoxInfo;
import com.linkgem.domain.common.exception.BusinessException;
import com.linkgem.domain.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class GemBoxPersistenceImpl implements GemBoxPersistence {

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
    public Optional<GemBox> findDefault(Long userId) {
        return repository.findByUserIdAndIsDefault(userId, true);
    }

    @Override
    public GemBox get(Long id, Long userId) {
        return this.find(id, userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.GEMBOX_NOT_FOUND));
    }

    @Override
    public List<GemBox> findAll(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Page<GemBoxInfo.Search> search(Long userId, Pageable pageable) {
        return repository.search(userId, pageable);
    }

    @Override
    public GemBox create(GemBox gembox) {
        return repository.save(gembox);
    }

    @Override
    public void delete(GemBox gemBox) {
        repository.delete(gemBox);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        repository.deleteAllByUserId(userId);
    }
}