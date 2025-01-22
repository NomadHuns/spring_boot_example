package com.example.spring_boot_jpa_example._core.utils;

import com.example.spring_boot_jpa_example._core.exception.RestException400;
import com.example.spring_boot_jpa_example._core.exception.ExceptionMessage;
import org.springframework.validation.Errors;

/*
    클라이언트로 부터 입력받은 값의 유효성 확인 후 에러 처리를 진행할 클래스입니다.
 */
public class ValidErrorUtil {

    public static void errorCheck(Errors errors) {
        if (errors.hasErrors()) {
            LoggerUtil.info(errors.getAllErrors()
                    .get(0)
                    .getDefaultMessage());
            throw new RestException400(ExceptionMessage.COMMON_VALID_FAIL.getCode(), errors.getAllErrors()
                    .get(0)
                    .getDefaultMessage());
        }
    }
}
