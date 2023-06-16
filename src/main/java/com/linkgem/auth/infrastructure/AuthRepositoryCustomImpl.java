package com.linkgem.auth.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthRepositoryCustomImpl implements AuthRepositoryCustom{

	private final JPAQueryFactory queryFactory;

}
