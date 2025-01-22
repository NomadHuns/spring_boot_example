package com.example.spring_boot_jpa_example.module.auth;

import com.example.spring_boot_jpa_example._core.utils.APIUtils;
import com.example.spring_boot_jpa_example._core.utils.ValidErrorUtil;
import com.example.spring_boot_jpa_example.module.auth.dtos.LoginRequestDTO;
import com.example.spring_boot_jpa_example.module.auth.dtos.LoginResponseDTO;
import com.example.spring_boot_jpa_example.module.auth.dtos.ReLoginRequestDTO;
import com.example.spring_boot_jpa_example.module.auth.dtos.ReLoginResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController // RestAPI로 요청하고 응답할 컨트롤러를 구현할때 사용되는 어노테이션.
@RequiredArgsConstructor // 해당 클래스의 final로 지정된 필드를 가지는 생성자를 자동으로 생성하는 어노테이션. 의존성 주입을 위해 사용됨.
@RequestMapping("/api/v1/auth") // 클래스의 매핑될 url을 정의하는 어노테이션.
public class AuthRestController {
    private final AuthImpService authImpService;

    /*
          로그인 Rest API
          url: /api/v1/auth/login
          method: POST
      */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO requestDTO,
                                   Errors errors) {

        ValidErrorUtil.errorCheck(errors);

        LoginResponseDTO responseDTO = authImpService.rest_login(requestDTO);

        return ResponseEntity.ok(APIUtils.success(responseDTO));
    }

    /*
        리프래시 토큰을 사용하여 토큰 재발급 Rest API
        url: /api/v1/auth/re-login
        method: POST
    */
    @PostMapping("/re-login")
    public ResponseEntity<?> reLogin(@RequestBody @Valid ReLoginRequestDTO requestDTO,
                                     Errors errors) {

        ValidErrorUtil.errorCheck(errors);

        ReLoginResponseDTO responseDTO = authImpService.rest_reLogin(requestDTO);

        return ResponseEntity.ok(APIUtils.success(responseDTO));
    }
}
