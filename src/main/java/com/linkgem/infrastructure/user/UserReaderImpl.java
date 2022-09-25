package com.linkgem.infrastructure.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.user.User;
import com.linkgem.domain.user.UserReader;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserReaderImpl implements UserReader {

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
}
