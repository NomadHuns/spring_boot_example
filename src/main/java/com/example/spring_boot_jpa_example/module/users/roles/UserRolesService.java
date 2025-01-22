package com.example.spring_boot_jpa_example.module.users.roles;

import com.example.spring_boot_jpa_example._core.exception.RestException400;
import com.example.spring_boot_jpa_example._core.exception.ExceptionMessage;
import com.example.spring_boot_jpa_example.module.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRolesService {
    private final UserRolesRepository userRolesRepository;

    public Page<UserRoles> page(Pageable pageable) {
        return userRolesRepository.findAll(pageable);
    }

    public UserRoles findByUserId(Long userId) {
        return userRolesRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RestException400(ExceptionMessage.NOT_FOUND_USER_ROLE.getCode(),
                        ExceptionMessage.NOT_FOUND_USER_ROLE.getMessage()));
    }

    @Transactional
    public UserRoles save(Long id, Users user, Roles role) {

        UserRoles userRolesForSave = UserRoles.builder()
                .id(id)
                .user(user)
                .role(role)
                .updateDate(LocalDateTime.now())
                .build();
        return userRolesRepository.save(userRolesForSave);
    }
}
