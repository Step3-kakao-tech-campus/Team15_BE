package com.kakao.borrowme._core.errors;

import com.kakao.borrowme._core.errors.exception.*;
import com.kakao.borrowme._core.utils.ApiUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Error-Reason", e.getReason()); // 에러 이유를 헤더에 추가

        return new ResponseEntity<>(e.body(), headers, e.status());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e){
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Error-Reason", e.getReason());

        return new ResponseEntity<>(e.body(), headers, e.status());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e){
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Error-Reason", e.getReason());

        return new ResponseEntity<>(e.body(), headers, e.status());
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e){
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Error-Reason", e.getReason());

        return new ResponseEntity<>(e.body(), headers, e.status());
    }

    @ExceptionHandler(Exception409.class)
    public ResponseEntity<?> conflict(Exception409 e){
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Error-Reason", e.getReason());

        return new ResponseEntity<>(e.body(), headers, e.status());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e){
        String reason = "internal_server_error"; // 내부 서버 오류를 나타내는 이유
        ApiUtils.ApiResult<?> apiResult = ApiUtils.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, reason);

        return new ResponseEntity<>(apiResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
