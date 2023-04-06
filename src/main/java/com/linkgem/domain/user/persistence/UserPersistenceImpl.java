package com.linkgem.domain.user.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.user.domain.User;
import com.linkgem.domain.common.exception.BusinessException;
import com.linkgem.domain.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserPersistenceImpl implements UserPersistence {

    private final UserRepository repository;

    @Override
    public Optional<User> find(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> findByLoginEmail(String loginEmail) {
        return repository.findByLoginEmail(loginEmail);
    }

    @Override
    public boolean existsByNickname(String nickName) {
        return repository.existsByNickname(nickName);
    }

    @Override
    public Optional<User> findByNickname(String nickName) {
        return repository.findByNickname(nickName);
    }

    @Override
    public User get(Long id) {
        return this.find(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User create(User user) {
        return repository.save(user);
    }
}
