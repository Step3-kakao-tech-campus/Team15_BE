package com.kakao.borrowme._core.errors;

import com.kakao.borrowme._core.errors.exception.Exception400;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Arrays;

public class GlobalValidationHandler {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() { }

    @Before("postMapping()")
    public void validationAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        Arrays.stream(args)
                .filter(arg -> arg instanceof Errors)
                .forEach(arg -> {
                    Errors errors = (Errors) arg;
                    if (errors.hasErrors()) {
                        FieldError error = errors.getFieldErrors().get(0);
                        throw new Exception400(error.getDefaultMessage() + ":" + error.getField(), "format");
                    }
                });
    }
}
