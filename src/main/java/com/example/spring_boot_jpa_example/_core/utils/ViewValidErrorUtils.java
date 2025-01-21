package com.example.spring_boot_jpa_example._core.utils;

import com.example.spring_boot_jpa_example._core.exception.ViewException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

public class ViewValidErrorUtils {

    public static void errorCheck(Errors errors) {
        if (errors.hasErrors()) {
            LoggerUtil.info(errors.getAllErrors()
                    .get(0)
                    .getDefaultMessage());
            throw new ViewException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public static void errorCheck(Errors errors, String location) {
        if (errors.hasErrors()) {
            LoggerUtil.info(errors.getAllErrors()
                    .get(0)
                    .getDefaultMessage());
            throw new ViewException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST, location);
        }
    }
}
