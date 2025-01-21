package com.example.spring_boot_jpa_example._core.exception;

import com.example.spring_boot_jpa_example._core.utils.APIUtils;
import org.springframework.http.HttpStatus;

/*
    개발자가 정의한 공통 예외 클래스입니다.
    해당 클래스는 400에러(클라이언트의 잘못된 요청)을 처리하기 위한 예외 클래스입니다.
 */

public class Exception400 extends RuntimeException {
    Integer code;
    public Exception400(int code, String message) {
        super(message);
        this.code = code;
    }

    public APIUtils.APIResult<?> body(){
        if (code == null) {
            return APIUtils.error(4, getMessage(), HttpStatus.BAD_REQUEST);
        } else {
            return APIUtils.error(code, getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}
