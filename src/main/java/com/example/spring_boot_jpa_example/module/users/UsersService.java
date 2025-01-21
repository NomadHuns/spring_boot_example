package com.example.spring_boot_jpa_example.module.users;

import com.example.spring_boot_jpa_example._core.exception.Exception400;
import com.example.spring_boot_jpa_example._core.exception.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service // 주로 로직을 처리하는 클래스에 정의되는 어노테이션.
@RequiredArgsConstructor // 해당 클래스의 final로 지정된 필드를 가지는 생성자를 자동으로 생성하는 어노테이션. 의존성 주입을 위해 사용됨.
@Transactional(readOnly = true) // 메서드나 클래스 레벨에서 트랜잭션 동작을 제어하기 위해 사용됩니다. 트랜잭션이 데이터베이스에 **쓰기 작업(INSERT, UPDATE, DELETE)**을 하지 않도록 보장합니다.
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    // id 필드 값으로 유저 엔티티 객체 조회 메서드
    public Users findById(long id) {
        return usersRepository.findById(id) // 해당 엔티티 객체를 조회
                // 만약 객체가 존재하지 않은 경우 예외처리합니다.
                .orElseThrow(() -> new Exception400(ExceptionMessage.NOT_FOUND_USER.getCode(),
                        ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }

    @Transactional // 메서드나 클래스 레벨에서 트랜잭션 동작을 제어하기 위해 사용됩니다. 트랜잭션이 활성화되면 데이터 수정, 삭제와 같은 작업이 수행 가능합니다. 변경 감지(Dirty Checking)를 활성화하여 엔티티 상태 변화를 DB에 반영합니다.
    public Users save(Long id, String username, String password, String email, UserStatus userStatus) {
        // 유저네임 중복 체크
        Users existedUsername = checkUsername(username);
        Users existedEmail = checkUsername(username);

        // 예외 처리
        if (existedUsername != null && !Objects.equals(existedUsername.getId(), id)) {
            throw new Exception400(ExceptionMessage.CAN_NOT_USE_USERNAME.getCode(),
                    ExceptionMessage.CAN_NOT_USE_USERNAME.getMessage());
        }
        // 예외 처리
        if (existedEmail != null && !Objects.equals(existedEmail.getId(), id)) {
            throw new Exception400(ExceptionMessage.CAN_NOT_USE_EMAIL.getCode(),
                    ExceptionMessage.CAN_NOT_USE_EMAIL.getMessage());
        }

        // 패스워드 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 빌드 패턴을 사용하여 유저 엔티티 객체룰 생성합니다.
        Users userForSave = Users.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(encodedPassword)
                .status(userStatus)
                .build();

        // 해당 엔티티 객체를 데이터베이스에 저장 요청합니다.
        return usersRepository.save(userForSave);
    }

    // username 필드 값으로 유저 엔티티 객체 조회 메서드
    public Users checkUsername(String username) {
        Optional<Users> userOP = usersRepository.findByUsername(username);
        // 조회 후 존재하지 않은 경우 null을 리턴합니다.
        return userOP.orElse(null);
    }

    // username 필드 값으로 유저 엔티티 객체 조회 메서드
    public Users checkEmail(String email) {
        Optional<Users> userOP = usersRepository.findByEmail(email);
        // 조회 후 존재하지 않은 경우 null을 리턴합니다.
        return userOP.orElse(null);
    }
}
