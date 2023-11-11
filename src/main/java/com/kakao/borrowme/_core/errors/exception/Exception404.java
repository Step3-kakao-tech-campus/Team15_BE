package com.kakao.borrowme._core.errors.exception;

import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 권한 없음
@Getter
public class Exception404 extends RuntimeException {
    private final String reason;

    public Exception404(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(getMessage(), HttpStatus.NOT_FOUND, reason);
    }

    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }
}
