package com.example.spring_boot_jpa_example.module.users.roles;

import com.example.spring_boot_jpa_example._core.exception.Exception400;
import com.example.spring_boot_jpa_example._core.exception.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RolesService {
    private final RolesRepository rolesRepository;

    // 역할을 확인하는 메서드
    public Roles checkRole(String role) {
        Roles rolePS = rolesRepository.findByName(role)
                .orElseThrow(() -> new Exception400(ExceptionMessage.INVALID_ROLE.getCode(),
                        ExceptionMessage.INVALID_ROLE.getMessage()));

        return rolePS;
    }
}
