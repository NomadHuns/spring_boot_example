package com.example.spring_boot_jpa_example._core.utils;

import lombok.extern.slf4j.Slf4j;

/*
    로그를 남길 때 사용되는 로그유틸 클래스입니다.
    다음 클래스를 사용하여 로그를 남겨야 application.yml에 정의된 로그레벨에 맞게 출력을 제어할 수 있습니다.
 */
@Slf4j
public class LoggerUtil {

    public static void debug(String message) {
        log.debug("디버그: " + message);
    }

    public static void info(String message) {
        log.info("info: " + message);
    }

    public static void trace(String message) {
        log.trace("trace: " + message);
    }

    public static void warn(String message) {
        log.warn("warn: " + message);
    }

    public static void error(String message) {
        log.error("error: " + message);
    }
}
