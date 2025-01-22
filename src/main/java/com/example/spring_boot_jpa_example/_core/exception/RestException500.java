package com.example.spring_boot_jpa_example._core.exception;

import com.example.spring_boot_jpa_example._core.utils.APIUtils;
import org.springframework.http.HttpStatus;

/*
    개발자가 정의한 공통 예외 클래스입니다.
    해당 클래스는 500에러(서버 에러)을 처리하기 위한 예외 클래스입니다.
 */
public class RestException500 extends RuntimeException {
    Integer code;

    public RestException500(int code, String message) {
        super(message);
        this.code = code;
    }

    public APIUtils.APIResult<?> body(){
        if (code == null) {
            return APIUtils.error(3, getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return APIUtils.error(code, getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public HttpStatus status(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
