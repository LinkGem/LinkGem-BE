package com.linkgem.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserPhase {

  FIRST_STEP("oauth"),
  SECOND_STEP("complete");

  private final String phase;
}
