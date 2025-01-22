package com.example.spring_boot_jpa_example.pages;

import com.example.spring_boot_jpa_example._core.security.CustomUserDetails;
import com.example.spring_boot_jpa_example._core.utils.ViewValidErrorUtils;
import com.example.spring_boot_jpa_example.module.auth.AuthImpService;
import com.example.spring_boot_jpa_example.module.auth.dtos.LoginRequestDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthImpService authImpService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "auth/login";
    }

    @PostMapping("/login")
    public String insert(@Valid LoginRequestDTO requestDTO, Errors errors, HttpServletResponse response) throws UnsupportedEncodingException {
        ViewValidErrorUtils.errorCheck(errors);
        var responseDTO = authImpService.login(requestDTO);

        String encodedValue = URLEncoder.encode(responseDTO.getToken().getAccessToken(), StandardCharsets.UTF_8);
        Cookie accessTokenCookie = new Cookie("accessToken", encodedValue);
        accessTokenCookie.setPath("/");     // 쿠키가 애플리케이션 전체에 적용
        accessTokenCookie.setMaxAge(60 * 60 * 24); // 24시간 동안 유효
        response.addCookie(accessTokenCookie);

        encodedValue = URLEncoder.encode(responseDTO.getToken().getAccessToken(), StandardCharsets.UTF_8);
        Cookie refreshTokenCookie = new Cookie("refreshToken", encodedValue);
        refreshTokenCookie.setPath("/");     // 쿠키가 애플리케이션 전체에 적용
        refreshTokenCookie.setMaxAge(60 * 60 * 24); // 24시간 동안 유효
        response.addCookie(refreshTokenCookie);

        return "redirect:/auth/test";
    }

    @GetMapping("/test")
    public String test(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        model.addAttribute("test", customUserDetails.userAuthority().getUser().getUsername());
        return "user/user";
    }
}
