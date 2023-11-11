package com.kakao.borrowme._core.errors.exception;

import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class Exception400 extends RuntimeException {

    private final String reason;

    public Exception400(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(getMessage(), HttpStatus.BAD_REQUEST, reason);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
