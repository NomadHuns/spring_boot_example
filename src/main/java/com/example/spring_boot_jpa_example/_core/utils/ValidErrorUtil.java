package com.example.spring_boot_jpa_example._core.utils;

import com.example.spring_boot_jpa_example._core.exception.Exception400;
import com.example.spring_boot_jpa_example._core.exception.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;

/*
    클라이언트로 부터 입력받은 값의 유효성 확인 후 에러 처리를 진행할 클래스입니다.
 */
@Slf4j
public class ValidErrorUtil {

    public static void errorCheck(Errors errors) {
        if (errors.hasErrors()) {
            LoggerUtil.info(errors.getAllErrors()
                    .get(0)
                    .getDefaultMessage());
            throw new Exception400(ExceptionMessage.COMMON_VALID_FAIL.getCode(), errors.getAllErrors()
                    .get(0)
                    .getDefaultMessage());
        }
    }
}
