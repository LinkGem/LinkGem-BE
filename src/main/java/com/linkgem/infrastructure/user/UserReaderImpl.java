package com.linkgem.infrastructure.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkgem.domain.user.User;
import com.linkgem.domain.user.UserReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserReaderImpl implements UserReader {

    private final UserRepository repository;

    @Override
    public Optional<User> find(Long id) {
        return repository.findById(id);
    }
}
