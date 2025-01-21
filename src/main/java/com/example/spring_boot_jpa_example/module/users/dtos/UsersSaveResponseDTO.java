package com.example.spring_boot_jpa_example.module.users.dtos;


import com.example.spring_boot_jpa_example._common.UsersDTO;

public record UsersSaveResponseDTO(
        UsersDTO user
) {
}
