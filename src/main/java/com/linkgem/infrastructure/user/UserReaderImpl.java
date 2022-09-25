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
    public User get(Long id) {
        return this.find(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
