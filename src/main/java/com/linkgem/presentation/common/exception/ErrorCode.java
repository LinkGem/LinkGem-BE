package com.linkgem.presentation.common.exception;

import com.linkgem.domain.gembox.GemBox;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    GEMBOX_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 잼박스 입니다"),
    GEMBOX_ALREADY_EXISTED(HttpStatus.CONFLICT, "이미 존재하는 잼박스 입니다."),
    GEMBOX_IS_FULL(HttpStatus.BAD_REQUEST, String.format("%s%d%s", "잼박스는 최대", GemBox.MAX_GEMBOX, "개 까지 저장이 가능합니다.")),
    GEMBOX_NOT_CHOOSE(HttpStatus.BAD_REQUEST, String.format("머지 할 잼박스를 두 개 이상 선택하세요.")),
    ACCESS_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "엑세스 토큰이 만료되었습니다."),
    ACCESS_TOKEN_NOT_EXPIRED(HttpStatus.BAD_REQUEST, "엑세스 토큰이 만료되지않았습니다."),
    ACCESS_TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "엑세스 토큰이 유효하지않습니다."),
    ACCESS_TOKEN_IS_EMPTY(HttpStatus.BAD_REQUEST, "엑세스 토큰이 없습니다."),
    REFRESH_TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "리프레시 토큰이 유효하지않습니다."),

    MAIL_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"메일 송신중에 오류가 발생했습니다."),

    LINK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 링크입니다."),

    USER_NICKNAME_NOT_VALID(HttpStatus.BAD_REQUEST, "닉네임이 유효하지않습니다."),
    CAREER_YEAR_NOT_VALID(HttpStatus.BAD_REQUEST, "커리어 연차가 유효하지않습니다."),
    JOB_NOT_VALID(HttpStatus.BAD_REQUEST, "직업이 유효하지않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다"),
    USER_NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    FILE_EMPTY(HttpStatus.BAD_REQUEST, "빈 파일입니다."),
    S3_FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "S3 파일 업로드 시 오류가 발생했습니다."),

    NOTIFICATION_NOT_FOUND_CREATE_TYPE(HttpStatus.NOT_FOUND, "존재하지 않는 알림 생성 서비스입니다."),
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않은 알림입니다."),

    GOOGLE_ERROR(HttpStatus.BAD_REQUEST,"테스트중"),
    PROVIDER_NOT_VALID(HttpStatus.BAD_REQUEST,"PROVIDER가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
