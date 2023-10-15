package com.kakao.borrowme._core.errors.exception;


import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 인증 안됨
@Getter
public class Exception401 extends RuntimeException {

    private final String reason;

    public Exception401(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.UNAUTHORIZED, reason);
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}