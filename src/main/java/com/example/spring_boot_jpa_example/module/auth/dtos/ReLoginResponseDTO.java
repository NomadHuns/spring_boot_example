package com.example.spring_boot_jpa_example.module.auth.dtos;

import com.example.spring_boot_jpa_example._common.dtos.TokenDTO;
import com.example.spring_boot_jpa_example._common.dtos.UsersDTO;
import com.example.spring_boot_jpa_example.module.users.roles.UserRoles;
import lombok.Getter;

@Getter
public class ReLoginResponseDTO {
    private final TokenDTO token;
    private final UsersDTO user;

    public ReLoginResponseDTO(UserRoles userRole, String accessToken, String refreshToken) {
        this.token = new TokenDTO(accessToken, refreshToken);
        this.user = new UsersDTO(userRole);
    }

}
