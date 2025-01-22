package com.example.spring_boot_jpa_example._core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Exception500 extends RuntimeException {
    private HttpStatus status;
    private String location;

    public Exception500(String msg, String location) {
        super(msg);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.location = location;
    }

    public Exception500(String msg) {
        this(msg, null);
    }
}
