package com.example.spring_boot_jpa_example._core.security;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.spring_boot_jpa_example._core.exception.Exception403;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/*
     JWT(Authentication) 기반 인증을 처리하는 JWTAuthenticationFilter를 정의하고 있습니다.
     이 필터는 요청마다 한 번씩 실행되며(OncePerRequestFilter), 클라이언트 요청에서 JWT 토큰을 추출하고 이를 검증한 뒤,
     인증 객체를 생성하여 Spring Security의 컨텍스트에 설정합니다.
 */
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    final JWTProvider provider;

    public JWTAuthenticationFilter(JWTProvider provide) {
        this.provider = provide;
    }

    // Spring Security의 요청 필터 로직을 구현하는 메서드입니다.
    // 요청에서 JWT 토큰을 추출하고, 검증 및 인증 작업을 수행합니다.
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        // 요청에서 JWT 토큰을 가져오는 메서드를 호출
        var jwt = resolveToken(request);
        if (StringUtils.hasText(jwt)) {

            // 토큰이 존재하는지 확인합니다.
            if (!provider.validateToken(jwt)) {
                throw new Exception403("접근이 거부되었습니다.");
            }

            try {
                // provider.getAuthentication(jwt)를 호출하여 토큰에서 인증 객체(Authentication)를 생성합니다.
                // 이 인증 객체는 사용자 권한 및 정보를 포함하며, Spring Security의 SecurityContext에 설정됩니다.
                // 설정된 인증 객체는 이후 요청 처리 중 접근 제어에 사용됩니다.
                var authentication = provider.getAuthentication(jwt);
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            // JWT가 만료되었거나 서명이 유효하지 않을 경우 발생하는 예외를 처리합니다.
            } catch (TokenExpiredException | SignatureVerificationException exception) {
                exception.printStackTrace();
            }
        }
        chain.doFilter(request, response);
    }

    // 요청에서 JWT 토큰을 가져오는 메서드를 호출합니다.
    // 쿠키 또는 헤더에서 토큰을 추출합니다.
    String resolveToken(HttpServletRequest request) {
        // 헤더에서 토큰 가져오기
        String bearerToken = request.getHeader(JWTProvider.HEADER);

        // 토큰이 존재하는지 확인합니다.
        if (StringUtils.hasText(bearerToken)) {
            return bearerToken;
        }

        // 쿠키에서 accessToken 가져오기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName()
                        .equals("accessToken")) {
                    return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                }
            }
        }
        return null;
    }
}
