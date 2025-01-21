package com.example.spring_boot_jpa_example._core.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    클라이언트 입력값 유효성 확인시 사용될 정규표현식을 정의한 유틸 클래스입니다.
 */
@Component
public class ExpValidUtils {
    // 정규표현식 패턴
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String ENGLISH_AND_KOREAN_REGEX = "^[a-zA-Z가-힣\\s]+$";
    private static final Pattern ENGLISH_AND_KOREAN_PATTERN = Pattern.compile(ENGLISH_AND_KOREAN_REGEX);
    private static final String ENGLISH_AND_NUMBER_REGEX = "^[a-zA-Z0-9\\s]+$";
    private static final Pattern ENGLISH_AND_NUMBER_PATTERN = Pattern.compile(ENGLISH_AND_NUMBER_REGEX);
    private static final String PHONE_NUMBER_REGEX = "\\d{2,3}-\\d{3,4}-\\d{4}$";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
    private static final String IDENTIFICATION_NUMBER_REGEX = "^\\d{6}-\\d{1}$|^\\d{6}-\\d{7}$|^\\d{6}$";
    private static final Pattern IDENTIFICATION_NUMBER_PATTERN = Pattern.compile(IDENTIFICATION_NUMBER_REGEX);

    public boolean isValidEmpty(Long value) {
        return value != null;
    }

    public boolean isValidEmpty(LocalDate value) {
        return value != null;
    }

    public boolean isValidEmpty(Integer value) {
        return value != null;
    }

    public boolean isValidEmpty(String value) {
        return value != null && !value.isEmpty();
    }


    public boolean isValidEmail(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        // Matcher로 정규표현식 체크
        Matcher matcher = EMAIL_PATTERN.matcher(value);
        return matcher.matches();  // true or false 반환
    }

    public boolean isValidEnglishAndKorean(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        // Matcher로 정규표현식 체크
        Matcher matcher = ENGLISH_AND_KOREAN_PATTERN.matcher(value);
        return matcher.matches();  // true or false 반환
    }

    public boolean isValidEnglishAndNumber(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        // Matcher로 정규표현식 체크
        Matcher matcher = ENGLISH_AND_NUMBER_PATTERN.matcher(value);
        return matcher.matches();  // true or false 반환
    }

    public boolean isValidPhoneNumber(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        // Matcher로 정규표현식 체크
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(value);
        return matcher.matches();  // true or false 반환
    }

    public boolean isValidIdentificationNumber(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        // Matcher로 정규표현식 체크
        Matcher matcher = IDENTIFICATION_NUMBER_PATTERN.matcher(value);
        return matcher.matches();  // true or false 반환
    }
}
