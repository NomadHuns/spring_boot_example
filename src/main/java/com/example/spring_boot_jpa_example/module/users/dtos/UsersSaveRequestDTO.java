package com.example.spring_boot_jpa_example.module.users.dtos;

import com.example.spring_boot_jpa_example._core.exception.ValidExceptionMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsersSaveRequestDTO(
        @NotEmpty(message = ValidExceptionMessage.Message.INVALID_USERNAME)
        @Size(min = 4, max = 15, message = ValidExceptionMessage.Message.INVALID_USERNAME)
        @Pattern(regexp = "^[a-zA-Z0-9._!@#-]+$", message = ValidExceptionMessage.Message.INVALID_USERNAME)
        String username,

        @NotEmpty(message = ValidExceptionMessage.Message.INVALID_PASSWORD)
        @Size(min = 4, max = 20, message = ValidExceptionMessage.Message.INVALID_PASSWORD)
        @Pattern(regexp = "^[a-zA-Z0-9._!@#-]+$", message = ValidExceptionMessage.Message.INVALID_PASSWORD)
        String password,

        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = ValidExceptionMessage.Message.INVALID_EMAIL
        )
        @NotEmpty(message = ValidExceptionMessage.Message.INVALID_EMAIL)
        String email
) 
{}
