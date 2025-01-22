package com.example.spring_boot_jpa_example._common.dtos;

import com.example.spring_boot_jpa_example.module.users.roles.UserRoles;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UsersDTO {
    private final Long id;
    private final String username;
    private final String role;
    private final String email;
    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;
    private final String status;

    public UsersDTO(UserRoles userRole) {
        this.id = userRole.getUser().getId();
        this.username = userRole.getUser().getUsername();
        this.role = userRole.getRole().getName();
        this.email = userRole.getUser().getEmail();
        this.createDate = userRole.getUser().getCreateDate();
        this.updateDate = userRole.getUser().getUpdateDate();
        this.status = userRole.getUser().getStatus().name();
    }
}