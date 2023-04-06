package com.linkgem.domain.auth.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthRepositoryCustomImpl implements AuthRepositoryCustom{

	private final JPAQueryFactory queryFactory;

}
