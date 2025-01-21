package com.example.spring_boot_jpa_example.module.users.dtos;

import com.example.spring_boot_jpa_example._common.PageDTO;
import com.example.spring_boot_jpa_example._common.UsersDTO;
import com.example.spring_boot_jpa_example.module.users.roles.UserRoles;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class UsersPageResponseDTO {
    private final PageDTO<UsersDTO> page;

    public UsersPageResponseDTO(Page<UserRoles> userRolesPage) {
        List<UserRoles> users = userRolesPage.getContent();
        List<UsersDTO> dtos = users.stream().map(UsersDTO::new).toList();

        this.page = new PageDTO<>(userRolesPage, dtos);
    }
}