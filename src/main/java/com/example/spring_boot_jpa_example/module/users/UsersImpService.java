package com.example.spring_boot_jpa_example.module.users;

import com.example.spring_boot_jpa_example._common.dtos.UsersDTO;
import com.example.spring_boot_jpa_example.module.users.dtos.UsersSaveRequestDTO;
import com.example.spring_boot_jpa_example.module.users.dtos.UsersDetailResponseDTO;
import com.example.spring_boot_jpa_example.module.users.dtos.UsersPageResponseDTO;
import com.example.spring_boot_jpa_example.module.users.dtos.UsersSaveResponseDTO;
import com.example.spring_boot_jpa_example.module.users.roles.RolesService;
import com.example.spring_boot_jpa_example.module.users.roles.UserRolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersImpService {
    private final UserRolesService userRolesService;
    private final UsersService usersService;
    private final RolesService rolesService;

    public UsersPageResponseDTO page(Pageable pageable) {
        var page = userRolesService.page(pageable);

        return new UsersPageResponseDTO(page);
    }

    public UsersDetailResponseDTO detail(long id) {
        var userRole = userRolesService.findByUserId(id);

        var userDTO = new UsersDTO(userRole);

        return new UsersDetailResponseDTO(userDTO);
    }

    public UsersSaveResponseDTO save(UsersSaveRequestDTO requestDTO) {

        var users =
                usersService.save(null, requestDTO.username(), requestDTO.password(), requestDTO.email(), UserStatus.WAIT);

        var userRole = userRolesService.save(null, users, rolesService.checkRole("MEMBER"));

        var userDTO = new UsersDTO(userRole);

        return new UsersSaveResponseDTO(userDTO);
    }
}
