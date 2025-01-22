package com.example.spring_boot_jpa_example._core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Exception401 extends RuntimeException {
    private HttpStatus status;
    private String location;

    public Exception401(String msg, String location) {
        super(msg);
        this.status = HttpStatus.UNAUTHORIZED;
        this.location = location;
    }

    public Exception401(String msg) {
        this(msg, null);
    }
}
