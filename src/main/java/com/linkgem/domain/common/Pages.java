package com.linkgem.domain.common;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pages<T> {

    public final List<T> contents;

    public final long totalCount;

    public final int size;

    public final int page;

}
