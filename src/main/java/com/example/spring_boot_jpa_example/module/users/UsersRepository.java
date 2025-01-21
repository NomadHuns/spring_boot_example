package com.example.spring_boot_jpa_example.module.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
    JpaRepository에 기본적인 CRUD가 정의되어 있지만 필요한 경우 직접 메서드를 정의할 수 있습니다.
 */
public interface UsersRepository extends JpaRepository<Users, Long> { // JpaRepository<엔티티로 사용된 클래스, 해당 클래스의 @id의 필드 타입>
    // Where 절 조건이 username 필드의 값이 일치하는 것을 하나 찾습니다.
    Optional<Users> findByUsername(String username);
    // Where 절 조건이 username 필드와 email 필드의 값이 일치하는 것을 하나 찾습니다.
    Optional<Users> findByUsernameAndEmail(String username, String email);
    // Where 절 조건이 email 필드의 값이 일치하는 것을 하나 찾습니다.
    Optional<Users> findByEmail(String email);
}
