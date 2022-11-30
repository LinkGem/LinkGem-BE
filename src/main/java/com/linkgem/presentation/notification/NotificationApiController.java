package com.linkgem.presentation.notification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkgem.application.NotificationFacade;
import com.linkgem.domain.common.Pages;
import com.linkgem.domain.notification.NotificationCommand;
import com.linkgem.domain.notification.NotificationInfo;
import com.linkgem.domain.notification.NotificationQuery;
import com.linkgem.presentation.common.CommonResponse;
import com.linkgem.presentation.common.UserAuthenticationProvider;
import com.linkgem.presentation.notification.dto.NotificationRequest;
import com.linkgem.presentation.notification.dto.NotificationResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "알림")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/notification")
@RestController
public class NotificationApiController {

    private final NotificationFacade notificationFacade;

    @ApiOperation(value = "알림 목록 조회", notes = "알림 목록을 조회한다")
    @GetMapping
    public CommonResponse<Pages<NotificationResponse.Main>> findAll(
        HttpServletRequest httpServletRequest,
        @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);

        NotificationQuery.FindAll findAllQuery = NotificationQuery.FindAll.builder()
            .userId(userId)
            .searchStartDate(LocalDateTime.now().minusMonths(3))
            .build();

        Page<NotificationInfo.Main> notifications = notificationFacade.findAll(findAllQuery, pageable);

        List<NotificationResponse.Main> responses = notifications
            .getContent()
            .stream()
            .map(NotificationResponse.Main::of)
            .collect(Collectors.toList());

        return CommonResponse.of(
            Pages.<NotificationResponse.Main>builder()
                .contents(responses)
                .totalCount(notifications.getTotalElements())
                .size(pageable.getPageSize())
                .page(pageable.getPageNumber())
                .build()
        );
    }

    @ApiOperation(value = "읽지 않은 알림 수 조회", notes = "읽지 않은 알림 수를 조회한다")
    @GetMapping(value = "/has-new-notifications")
    public CommonResponse<NotificationResponse.NewNotificationInformation> getUnReadNotificationCount(
        HttpServletRequest httpServletRequest
    ) {

        Long userId = UserAuthenticationProvider.provider(httpServletRequest);

        NotificationQuery.FindAll findAllQuery = NotificationQuery.FindAll.builder()
            .userId(userId)
            .searchStartDate(LocalDateTime.now().minusDays(1))
            .build();

        return CommonResponse.of(
            new NotificationResponse.NewNotificationInformation(
                notificationFacade.getUnReadNotificationCount(findAllQuery))
        );
    }

    @ApiOperation(value = "알림 전송", notes = "알림을 전송한다")
    @PostMapping(value = "/{receiverId}")
    public CommonResponse<NotificationResponse.Main> sendNotification(
        HttpServletRequest httpServletRequest,
        @PathVariable Long receiverId,
        @Valid NotificationRequest.Create createRequest
    ) {
        NotificationCommand.Create createCommand = createRequest.toCommand(receiverId);

        return CommonResponse.of(NotificationResponse.Main.of(notificationFacade.create(createCommand)));
    }
}
