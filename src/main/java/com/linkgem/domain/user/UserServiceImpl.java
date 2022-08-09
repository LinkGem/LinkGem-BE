package com.linkgem.domain.user;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkgem.infrastructure.user.UserRepository;
import com.linkgem.presentation.common.exception.BusinessException;
import com.linkgem.presentation.common.exception.ErrorCode;
import com.linkgem.presentation.user.dto.UserRequest.AddDetailInfoRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;


  @Override
  @Transactional
  public void addDetailInfo(Long userId, AddDetailInfoRequest addDetailInfoRequest) {

    if (addDetailInfoRequest.getUserNickname().isBlank()) {
      throw new BusinessException(ErrorCode.USER_NICKNAME_NOT_VALID);
    } else if (userRepository.existsByNickname(addDetailInfoRequest.getUserNickname())) {
      throw new BusinessException(ErrorCode.USER_NICKNAME_ALREADY_EXIST);
    } else if (Objects.isNull(addDetailInfoRequest.getCareerYear())
        || addDetailInfoRequest.getCareerYear() < 0) {
      throw new BusinessException(ErrorCode.CAREER_YEAR_NOT_VALID);
    } else if (addDetailInfoRequest.getJobName().isBlank()) {
      throw new BusinessException(ErrorCode.JOB_NOT_VALID);
    }
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    user.updateCareerYear(addDetailInfoRequest.getCareerYear());
    user.updateJob(addDetailInfoRequest.getJobName());
    user.updateNickname(addDetailInfoRequest.getUserNickname());
    user.updateUserPhaseRegistered();

  }
}
