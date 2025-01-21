package com.example.spring_boot_jpa_example._core.exception;

import com.example.spring_boot_jpa_example._core.utils.APIUtils;
import org.springframework.http.HttpStatus;

/*
    개발자가 정의한 공통 예외 클래스입니다.
    해당 클래스는 401에러(인증되지 않은 요청)을 처리하기 위한 예외 클래스입니다.
 */
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public APIUtils.APIResult<?> body(){
        return APIUtils.error(1, getMessage(), HttpStatus.UNAUTHORIZED);
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}
