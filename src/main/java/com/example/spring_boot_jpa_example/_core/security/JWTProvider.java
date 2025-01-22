package com.example.spring_boot_jpa_example._core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.spring_boot_jpa_example._core.exception.RestException401;
import com.example.spring_boot_jpa_example._core.exception.RestException500;
import com.example.spring_boot_jpa_example._core.exception.ExceptionMessage;
import com.example.spring_boot_jpa_example.module.users.roles.UserRoles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

// 토큰 발행 및 유효성 확인 유틸
@Component
public class JWTProvider {
    // 액세스 토큰의 유효기간을 설정합니다.
    private static final Long ACCESS_EXP = 1000L * 60 * 60 * 3;
    // 리프래시 토큰의 유효기간을 설정합니다.
    private static final Long REFRESH_EXP = 1000L * 60 * 60 * 72;
    // 토큰의 앞에 붙을 문자열을 정의합니다. 토큰 인증 방식은 토큰값 앞에 Bearer를 붙이는 것을 권장합니다.
    public static final String TOKEN_PREFIX = "Bearer ";
    // 클라이언트가 요청시 요청 헤더 키값을 정의합니다.
    // 헤더에 토큰값을 넣어서 요청할 때 Authorization 키에 토큰값을 넣어야 인식됩니다.
    public static final String HEADER = "Authorization";
    // applicaion.yml 파일에 정의된 설정값을 읽어와서 값을 할당합니다.(jwt.secret) SECRET은 서버에서만 알고 있는 비밀 키입니다.
    @Value("${jwt.secret}")
    private String SECRET;

    private final CustomUserDetailsService userDetailsService;

    public JWTProvider(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    // 토큰 발행 메서드
    public String create(UserRoles userAuthority, JWTType jwtType) {

        // 토큰 생성
        String jwt = JWT.create()
                        // JWT의 sub(subject) 필드에 사용자 ID를 설정합니다. 일반적으로 이 값은 토큰의 소유자를 식별하기 위해 사용됩니다.
                        .withSubject(String.valueOf(userAuthority.getUser().getId()))
                        // 토큰의 만료 시간을 설정합니다.
                        .withExpiresAt(makeExpiresAt(jwtType))
                        // JWT의 사용자 정의 클레임을 추가합니다.
                        .withClaim("username", userAuthority.getUser().getUsername())
                        .withClaim("role", userAuthority.getRole().getName())
                        .withClaim("token-type", jwtType.name())
                        // HMAC512 알고리즘과 비밀 키(SECRET)를 사용하여 JWT를 서명합니다. SECRET은 서버에서만 알고 있는 비밀 키입니다.
                        .sign(Algorithm.HMAC512(SECRET));

        return TOKEN_PREFIX + jwt;
    }

    // 토큰의 유효기간을 설정하는 메서드입니다.
    private Date makeExpiresAt(JWTType jwtType) {
        if (jwtType.equals(JWTType.ACCESS_TOKEN)) {
            return new Date(System.currentTimeMillis() + ACCESS_EXP);
        } else if (jwtType.equals(JWTType.REFRESH_TOKEN)) {
            return new Date(System.currentTimeMillis() + REFRESH_EXP);
        } else {
            throw new RestException500(ExceptionMessage.INVALID_TOKEN_TYPE.getCode(), ExceptionMessage.INVALID_TOKEN_TYPE.getMessage());
        }
    }

    // 토큰을 검증하는 메서드입니다.
    public DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {

        if (jwt.startsWith(TOKEN_PREFIX)) {
            jwt = jwt.substring(TOKEN_PREFIX.length());
        }

        return JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(jwt);
    }

    // 토큰 자체의 유효성을 검증하는 메서드입니다.
    public boolean validateToken(String jwt) {
        try {
            var verify = verify(jwt);

            // 토큰의 유효기간을 확인합니다.
            if (new Date().after(verify.getExpiresAt()) ) {
                throw new RestException401("세션이 만료되었습니다.");
            }

            // 토큰 타입을 확인합니다.
            if (!verify.getClaim("token-type").asString().equals(JWTType.ACCESS_TOKEN.name())) {
                throw new RestException401("올바르지 않은 토큰입니다.");
            }

        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    // 토큰 값을 사용하여 인증 유저 객체를 생성하는 메서드입니다.
    public Authentication getAuthentication(String token) {

        var decodedJWT = verify(token);
        var username = decodedJWT.getClaim("username").asString();
        var userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
