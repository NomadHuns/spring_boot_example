package com.example.spring_boot_jpa_example._core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/*
    인증/인가 처리 등 요청시 공통처리할 부분을 정의하는 필터 클래스입니다.
    Controller에 도달하기 전 해당 클래스에 정의된 securityFilterChain이 먼저 실행됩니다.
    만약 해당 securityFilterChain에 인증/인가 필터가 적용된 경우,
    클라이언트로부터 받은 값의 유효성을 확인하기 전(Controller에서 처리함)에 인증/인가를 먼저 확인합니다.
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    // JWT 토큰이 적용된 필터의 경우 JWT 토큰 확인을 위한 클래스입니다.
    final JWTProvider provider;

    // 의존성 주입
    public SecurityConfig(JWTProvider provider) {
        this.provider = provider;
    }

    // 평문으로 입력된 패스워드를 암호화할 수 있도록 하는 인코더입니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 인증 및 권한 오류가 발생했을 때 처리 방식을 정의합니다.
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new JWTAuthenticationEntryPoint()) // 인증 실패 시 실행되는 핸들러를 설정합니다.
                        .accessDeniedHandler(new JWTAccessDeniedHandler()) // 권한이 없는 요청 시 실행되는 핸들러를 설정합니다.
                )
                .csrf(AbstractHttpConfigurer::disable) // CSRF(Cross-Site Request Forgery) 보호를 비활성화합니다. 주로 REST API 서버에서는 CSRF 보호가 필요 없기 때문에 비활성화합니다.
                .headers(AbstractHttpConfigurer::disable) // HTTP 응답 헤더 관련 기본 보안 구성을 비활성화합니다. 필요에 따라 보안 헤더 설정을 다시 추가해야 할 수 있습니다(예: Content-Security-Policy).
                .httpBasic(withDefaults()) // 시큐리티 라이브러리에서 제공하는 HTTP Basic 인증을 활성화합니다.
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 생성하지 않고, 모든 요청은 독립적으로 처리합니다. 주로 JWT 기반 인증에서 사용됩니다.
                )
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource())) // CORS(Cross-Origin Resource Sharing) 설정을 정의합니다. CORS 구성을 가져오는 메서드를 지정합니다.
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(WHITELIST).permitAll() // 인증 없이 접근 가능한 URL 경로 목록.
                                .requestMatchers(USER).hasAnyAuthority("USER","MANAGER","ADMIN") // 특정 권한이 필요한 경로.
                                .requestMatchers(MANAGER).hasAnyAuthority("MANAGER","ADMIN") // 관리자 또는 매니저 권한이 필요한 경로.
                                .requestMatchers(ADMIN).hasAnyAuthority("ADMIN") // 관리자만 접근 가능한 경로.
                                .anyRequest().authenticated() // 위에서 정의되지 않은 모든 요청은 인증 필요.
                )
                // 커스텀 필터 추가
                .addFilterBefore(new JWTAuthenticationFilter(provider), UsernamePasswordAuthenticationFilter.class); // JWT 토큰을 검사하고 인증 객체를 생성하는 필터입니다. UsernamePasswordAuthenticationFilter 앞에 실행되도록 추가됩니다.

        return http.build();
    }

    // CORS를 허용하는 도메인, ip주소등을 지정하는 메서드입니다.
    // 해당 메서드에 지정되지 않은 도메인, 아이피주소는 요청할 수 없습니다.
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*"); // 클라이언트가 서버로 요청할 때 헤더에 어떤 값이든 보낼 수 있습니다.
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드를 허용합니다. (Get, Post 등)
        configuration.setAllowedOrigins( // 이 설정은 지정된 출처에서 오는 요청만 허용합니다.
                List.of(
                        "http://localhost",
                        "http://localhost:8080"
                )
        );

        configuration.setAllowCredentials(true); // 클라이언트가 쿠키와 함께 요청을 보낼 수 있도록 허용합니다.
        configuration.addExposedHeader(JWTProvider.HEADER); // JWTProvider에서 정의한 헤더(Authorization)를 노출합니다.

        var source = new UrlBasedCorsConfigurationSource(); // CORS 설정을 URL 경로별로 매핑하는 객체를 생성합니다.
        source.registerCorsConfiguration("/**", configuration); // 모든 경로(/**)에 대해 정의한 CORS 설정을 적용합니다.
        return source;
    }

    public static final String[] MANAGER = {
            "/api/v1/stores/**",
    };

    public static final String[] ADMIN = {
            "/api/v1/admin/**",
            "/api/v1/users/**",
            "/api/v1/stores",
            "/api/v1/stores/**",
            "/api/v1/policies",
            "/api/v1/policies/**",
            "/api/v1/insurances",
            "/api/v1/insurances/**",
    };

    public static final String[] USER = {
            "/api/v1/product/**",
    };

    public static final String[] WHITELIST = {
            "/test",
            "/api/v1/auth/**",
            "/api/v1/shared/**",
            "/h2-console",
            "/h2-console/**",
            "/api/v1/shared",
            "/api/v1/shared/**",
            "/api/v1/retails",
            "/api/v1/retails/**",
            "/api/v1/telecoms",
            "/api/v1/telecoms/**",
            "/api/v1/price-plans",
            "/api/v1/price-plans/**",
            "/api/v1/phones",
            "/api/v1/phones/**",
            "/api/v1/additional-services",
            "/api/v1/additional-services/**",
    };
}