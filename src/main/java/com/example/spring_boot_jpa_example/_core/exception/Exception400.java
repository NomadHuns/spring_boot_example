package com.example.spring_boot_jpa_example._core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Exception400 extends RuntimeException {
    private HttpStatus status;
    private String location;

    public Exception400(String msg, String location) {
        super(msg);
        this.status = HttpStatus.BAD_REQUEST;
        this.location = location;
    }

    public Exception400(String msg) {
        this(msg, null);
    }
}
