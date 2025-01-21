package com.example.spring_boot_jpa_example.module.users.roles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
    JpaRepository에 기본적인 CRUD가 정의되어 있지만 필요한 경우 직접 메서드를 정의할 수 있습니다.
 */
public interface RolesRepository extends JpaRepository<Roles, Long> {
    // Where 절 조건이 관계된 User 테이블의 name 필드의 값이 일치하는 것을 하나 찾습니다.
    // 여기서는 테이블의 칼럼값이 role이지만, 객체의 필드값(name)을 사용합니다.
    Optional<Roles> findByName(String name);
}
