package com.linkgem.domain.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    /**
     * @RequestBody 예외 핸들러
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> bindMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {

        ErrorResponse response = ErrorResponse.builder()
            .code("MaxUploadSizeExceededException")
            .message(e.getMessage())
            .displayMessage("파일 업로드 용량 제한은 10MB입니다.")
            .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @RequestBody 예외 핸들러
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> bindHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ErrorResponse response = ErrorResponse.builder()
            .code("HttpMessageNotReadableException")
            .message(e.getMessage())
            .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @Valid 관련 예외 핸들러
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> bindException(BindException e) {
        ErrorResponse response = ErrorResponse.builder()
            .code("Bad Request")
            .message("Bad Request")
            .errors(e)
            .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> onBusinessException(BusinessException e) {

        ErrorCode errorCode = e.getErrorCode();

        final ErrorResponse response =
            ErrorResponse
                .builder()
                .code(errorCode.name())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> onException(Exception e) {

        log.error("", e);

        final ErrorResponse response =
            ErrorResponse
                .builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(e.toString())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
