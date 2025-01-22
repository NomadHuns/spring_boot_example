package com.example.spring_boot_jpa_example.module.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.spring_boot_jpa_example._core.exception.Exception400;
import com.example.spring_boot_jpa_example._core.exception.RestException400;
import com.example.spring_boot_jpa_example._core.exception.ExceptionMessage;
import com.example.spring_boot_jpa_example._core.security.JWTProvider;
import com.example.spring_boot_jpa_example._core.security.JWTType;
import com.example.spring_boot_jpa_example.module.auth.dtos.LoginRequestDTO;
import com.example.spring_boot_jpa_example.module.auth.dtos.LoginResponseDTO;
import com.example.spring_boot_jpa_example.module.auth.dtos.ReLoginRequestDTO;
import com.example.spring_boot_jpa_example.module.auth.dtos.ReLoginResponseDTO;
import com.example.spring_boot_jpa_example.module.users.Users;
import com.example.spring_boot_jpa_example.module.users.roles.UserRoles;
import com.example.spring_boot_jpa_example.module.users.roles.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthImpService {
    private final UserRolesRepository userRolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    public LoginResponseDTO rest_login(LoginRequestDTO requestDTO) {

        // 유저명으로 유저 불러오기
        UserRoles userRolePS = userRolesRepository.findByUser_Username(requestDTO.username())
                .orElseThrow(() -> new RestException400(ExceptionMessage.LOGIN_FAIL.getCode(), ExceptionMessage.LOGIN_FAIL.getMessage()));
        Users userPS = userRolePS.getUser();

        // 비밀번호가 일치하지 않을 때 400 예외 처리
        if (!passwordEncoder.matches(requestDTO.password(), userPS.getPassword())) {
            throw new RestException400(ExceptionMessage.LOGIN_FAIL.getCode(), ExceptionMessage.LOGIN_FAIL.getMessage());
        }

        // 토큰 생성
        String accessToken = jwtProvider.create(userRolePS, JWTType.ACCESS_TOKEN);
        String refreshToken = jwtProvider.create(userRolePS, JWTType.REFRESH_TOKEN);

        // 응답 DTO 리턴
        return new LoginResponseDTO(userRolePS, accessToken, refreshToken);
    }

    public ReLoginResponseDTO rest_reLogin(ReLoginRequestDTO requestDTO) {

        // 토큰 디코딩
        DecodedJWT decodedJWT = jwtProvider.verify(requestDTO.refreshToken());

        // 토큰 타입 확인
        if (!decodedJWT.getClaim("token-type")
                       .asString()
                       .equals(JWTType.REFRESH_TOKEN.name())) {
            throw new RestException400(ExceptionMessage.IS_NOT_REFRESH_TOKEN.getCode(), ExceptionMessage.IS_NOT_REFRESH_TOKEN.getMessage());
        }

        // 유저 불러오기
        UserRoles userRolePS = userRolesRepository.findByUser_Id(Long.parseLong(decodedJWT.getSubject()))
                        .orElseThrow(() -> new RestException400(ExceptionMessage.INVALID_TOKEN.getCode(), ExceptionMessage.INVALID_TOKEN.getMessage()));

        // 토큰 생성
        String accessToken = jwtProvider.create(userRolePS, JWTType.ACCESS_TOKEN);
        String refreshToken = jwtProvider.create(userRolePS, JWTType.REFRESH_TOKEN);

        return new ReLoginResponseDTO(userRolePS, accessToken, refreshToken);
    }

    public LoginResponseDTO login(LoginRequestDTO requestDTO) {

        // 유저명으로 유저 불러오기
        UserRoles userRolePS = userRolesRepository.findByUser_Username(requestDTO.username())
                .orElseThrow(() -> new Exception400(ExceptionMessage.LOGIN_FAIL.getMessage()));
        Users userPS = userRolePS.getUser();

        // 비밀번호가 일치하지 않을 때 400 예외 처리
        if (!passwordEncoder.matches(requestDTO.password(), userPS.getPassword())) {
            throw new Exception400(ExceptionMessage.LOGIN_FAIL.getMessage());
        }

        // 토큰 생성
        String accessToken = jwtProvider.create(userRolePS, JWTType.ACCESS_TOKEN);
        String refreshToken = jwtProvider.create(userRolePS, JWTType.REFRESH_TOKEN);

        // 응답 DTO 리턴
        return new LoginResponseDTO(userRolePS, accessToken, refreshToken);
    }
}
