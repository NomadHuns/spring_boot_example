package com.example.spring_boot_jpa_example.module.auth.dtos;

import com.example.spring_boot_jpa_example._core.exception.ValidExceptionMessage;
import jakarta.validation.constraints.NotEmpty;

public record ReLoginRequestDTO(
        @NotEmpty(message = ValidExceptionMessage.Message.EMPTY_TOKEN)
        String refreshToken
) {}
