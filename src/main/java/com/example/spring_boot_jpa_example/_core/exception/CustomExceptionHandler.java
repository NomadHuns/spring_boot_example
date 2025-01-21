package com.example.spring_boot_jpa_example._core.exception;

import com.example.spring_boot_jpa_example._core.utils.APIUtils;
import com.example.spring_boot_jpa_example._core.utils.CommonScriptUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
    예외 처리 시에 응답 데이터를 공통으로 처리하는 클래스입니다.
    exception 디렉터리에 자신이 정의한 Exception 클래스를 등록하면 됩니다.
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ViewException.class)
    public ResponseEntity<?> customException(ViewException e) {
        if (e.getLocation() == null) {
            return new ResponseEntity<>(CommonScriptUtils.back(e.getMessage()), e.getStatus());
        }
        return new ResponseEntity<>(CommonScriptUtils.herf(e.getMessage(), e.getLocation()), e.getStatus());
    }

    // Exception401 예외 처리 등록
    @ExceptionHandler(Exception401.class) // Exception401 예외 발생시 처리할 핸들러
    public ResponseEntity<?> unAuthorized(Exception401 e){
        return new ResponseEntity<>(e.body(), e.status()); // 응답 구조를 생성하여 사용자에게 응답함.
    }

    // Exception403 예외 처리 등록
    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    // Exception400 예외 처리 등록
    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    // Exception500 예외 처리 등록
    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> internalServerError(Exception500 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    // 그외 예외 처리 등록
    // 개발자가 핸들링 하지 못한 에러는 아래 메서드로 핸들링됩니다.
    // 기본적으로 응답 코드는 500으로 고정됩니다.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownInternalServerError(Exception e){
        return new ResponseEntity<>(
                APIUtils.error(3, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
