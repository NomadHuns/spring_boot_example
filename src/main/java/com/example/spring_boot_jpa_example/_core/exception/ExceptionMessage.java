package com.example.spring_boot_jpa_example._core.exception;

/*
    예외 처리 후 클라이언트에게 응답할 메시지를 정의한 enum입니다.
 */
public enum ExceptionMessage {
    TEST_EXCEPTION(0, "테스트 예외 에러입니다."),
    INVALID_TOKEN(1, "유효하지 않은 토큰입니다."),
    INVALID_TOKEN_TYPE(2, "올바르지 않은 토큰 타입입니다."),
    COMMON_AUTHORITY_FAIL(3, "권한이 없습니다."),
    COMMON_VALID_FAIL(4, "유효성 체크 오류"),
    NOT_FOUND_USER(5, "존재하지 않는 유저입니다."),
    CAN_NOT_USE_USERNAME(6, "중복되는 아이디입니다."),
    INVALID_ROLE(7, "존재하지 않는 역할입니다."),
    NOT_FOUND_USER_ROLE(8, "존재하지 않는 유저롤입니다.(데이터가 잘못된 것입니다. 서버 담당자를 찾으세요.)"),
    CAN_NOT_USE_EMAIL(9, "중복되는 이메일입니다."),
    // 예시: HELLO_EXCEPTION(0, "인사를 하지 않았습니다."),
    ;

    final private String message;
    final private Integer code;

    ExceptionMessage(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
    public Integer getCode() {
        return code;
    }
}
