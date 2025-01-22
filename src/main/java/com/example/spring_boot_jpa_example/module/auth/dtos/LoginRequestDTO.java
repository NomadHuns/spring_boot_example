package com.example.spring_boot_jpa_example.module.auth.dtos;

import com.example.spring_boot_jpa_example._core.exception.ValidExceptionMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotEmpty(message = ValidExceptionMessage.Message.INVALID_USERNAME)
        String username,

        @NotEmpty(message = ValidExceptionMessage.Message.INVALID_PASSWORD)
        String password
) 
{}
