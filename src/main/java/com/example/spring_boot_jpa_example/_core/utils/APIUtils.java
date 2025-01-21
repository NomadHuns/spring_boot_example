package com.example.spring_boot_jpa_example._core.utils;

import org.springframework.http.HttpStatus;

/*
    주로 Json 응답에 사용되는 유틸 클래스입니다.
 */
public class APIUtils {

    // 요청/응답 성공 시에 호출될 메서드
    public static <T> APIResult<T> success(T response) {
        return new APIResult<>(true, response, null);
    }

    // 요청/응답 실패 시에 호출될 메서드
    public static APIResult<?> error(int errorCode, String message, HttpStatus status) {
        return new APIResult<>(false, null, new APIError(errorCode, message, status.value()));
    }


    public record APIResult<T>(boolean success, T response, APIError error) {
    }

        public record APIError(int errorCode, String message, int status) {
    }
}
