package com.kakao.borrowme._core.errors.exception;

import org.springframework.http.HttpStatus;

public class InsufficientCoinException extends RuntimeException {
    public InsufficientCoinException(String message) {
        super(message);
    }
}
