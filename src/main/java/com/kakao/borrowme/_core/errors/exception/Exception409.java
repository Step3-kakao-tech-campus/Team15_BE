package com.kakao.borrowme._core.errors.exception;

import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 서버 에러
@Getter
public class Exception409 extends RuntimeException {

    private final String reason;

    public Exception409(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.CONFLICT, reason);
    }

    public HttpStatus status(){
        return HttpStatus.CONFLICT;
    }
}
