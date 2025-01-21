package com.example.spring_boot_jpa_example._core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ViewException extends RuntimeException {
    private HttpStatus status;
    private String location;

    public ViewException(String msg, HttpStatus status, String location) {
        super(msg);
        this.status = status;
        this.location = location;
    }

    public ViewException(String msg, HttpStatus status) {
        this(msg, status, null);
    }
}
