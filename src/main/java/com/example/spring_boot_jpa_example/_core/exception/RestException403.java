package com.example.spring_boot_jpa_example._core.exception;

import com.example.spring_boot_jpa_example._core.utils.APIUtils;
import org.springframework.http.HttpStatus;

/*
    개발자가 정의한 공통 예외 클래스입니다.
    해당 클래스는 403에러(접근 권한 없음)을 처리하기 위한 예외 클래스입니다.
 */
public class RestException403 extends RuntimeException {
    public RestException403(String message) {
        super(message);
    }

    public APIUtils.APIResult<?> body(){
        return APIUtils.error(2, getMessage(), HttpStatus.FORBIDDEN);
    }

    public HttpStatus status(){
        return HttpStatus.FORBIDDEN;
    }
}
