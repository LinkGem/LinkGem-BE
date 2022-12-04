package com.linkgem.presentation.notification;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkgem.application.NotificationFacade;
import com.linkgem.domain.common.Pages;
import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.domain.notification.NotificationType;
import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.common.UserAuthenticationProvider;
import com.linkgem.presentation.notification.dto.NotificationRequest;
import com.linkgem.presentation.notification.dto.NotificationResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "알림")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/notifications")
@RestController
public class NotificationApiController {

    private final NotificationFacade notificationFacade;

    private static final LocalDateTime SEARCH_START_DATE_TIME = LocalDateTime.now().minusMonths(3);

    @ApiOperation(value = "알림 목록 조회", notes = "알림 목록을 조회한다")
    @GetMapping
    public CommonResponse<Pages<NotificationResponse.Main>> findAll(
        NotificationRequest.FindAll findAllRequestDto,
        HttpServletRequest httpServletRequest,
        @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);

        NotificationQuery.FindAll findAllQuery = NotificationQuery.FindAll.builder()
            .isRead(findAllRequestDto.getIsRead())
            .userId(userId)
            .searchStartDateTime(SEARCH_START_DATE_TIME)
            .build();

        Page<NotificationInfo.Main> notifications = notificationFacade.findAll(findAllQuery, pageable);

        List<NotificationResponse.Main> responses =
            NotificationResponse.Main.ofs(notifications.getContent());

        return CommonResponse.of(
            Pages.<NotificationResponse.Main>builder()
                .contents(responses)
                .totalCount(notifications.getTotalElements())
                .size(pageable.getPageSize())
                .page(pageable.getPageNumber())
                .build()
        );
    }

    @ApiOperation(value = "최신 알림 정보 조회", notes = "최신 알림 정보를 조회한다")
    @GetMapping(value = "/latest-information")
    public CommonResponse<List<NotificationResponse.LatestNotification>> findAllLatestInformation(
        HttpServletRequest httpServletRequest) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);

        NotificationQuery.FindAllLatest findAllLatestQuery = NotificationQuery.FindAllLatest.builder()
            .userId(userId)
            .searchStartDateTime(SEARCH_START_DATE_TIME)
            .build();

        List<NotificationResponse.LatestNotification> responses =
            NotificationResponse.LatestNotification.ofs(notificationFacade.findAllLatest(findAllLatestQuery));

        return CommonResponse.of(responses);
    }

    @ApiOperation(value = "알림 타입 조회", notes = "알림 타입을 조회한다")
    @GetMapping(value = "/types")
    public CommonResponse<List<NotificationResponse.NotificationTypeResponse>> findAllNotificationType() {
        List<NotificationResponse.NotificationTypeResponse> responses =
            NotificationResponse.NotificationTypeResponse.ofs(Arrays.asList(NotificationType.values()));

        return CommonResponse.of(responses);
    }

    @ApiOperation(value = "알림 전송", notes = "알림을 전송한다")
    @PostMapping(value = "/{receiverId}")
    public CommonResponse<NotificationResponse.Main> sendNotification(
        @PathVariable Long receiverId,
        @Valid NotificationRequest.Create createRequest
    ) {
        NotificationCommand.Create createCommand = createRequest.toCommand(receiverId);

        return CommonResponse.of(NotificationResponse.Main.of(notificationFacade.create(createCommand)));
    }

    @ApiOperation(value = "알림 읽기 요청", notes = "알림 읽기를 요청한다")
    @PatchMapping(value = "/{notificationId}/is-read")
    public ResponseEntity<Void> updateNotification(
        @PathVariable long notificationId,
        HttpServletRequest httpServletRequest
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);

        NotificationCommand.Read command = NotificationCommand.Read.of(notificationId, userId);
        notificationFacade.readNotification(command);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "알림 삭제 요청", notes = "알림 삭제를 요청한다")
    @DeleteMapping(value = "/{notificationId}")
    public ResponseEntity<Void> deleteNotification(
        @PathVariable long notificationId,
        HttpServletRequest httpServletRequest
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);

        NotificationCommand.Delete command = NotificationCommand.Delete.of(notificationId, userId);
        notificationFacade.deleteNotification(command);

        return ResponseEntity.noContent().build();
    }
}
