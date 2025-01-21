package com.example.spring_boot_jpa_example._core.exception;

/*
    클라이언트 요청값의 유효성을 확인 후 유효하지 않은 경우 클라이언트에게 응답할 메시지를 정의한 enum입니다.
 */
public enum ValidExceptionMessage {
    USELESS(Message.INVALID_USERNAME);
    // 여기에는 작성하지 않아도 됩니다.

    final private String message;

    ValidExceptionMessage(String message) {
        this.message = message;
    }

    public static class Message {
        public static final String INVALID_USERNAME = "아이디는 4에서 15자 이내여야 합니다. 영문자, 숫자, '.', '-', '_', '!', '@', '#'만 허용됩니다.";
        public static final String INVALID_PASSWORD = "패스워드는 4에서 20자 이내여야 합니다. 영문자, 숫자, '.', '-', '_', '!', '@', '#'만 허용됩니다.";
        // 예시: public static final String INVALID_HELLO = "인사말을 작성하세요.";
    }
}
