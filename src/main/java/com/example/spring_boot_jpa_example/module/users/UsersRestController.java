package com.example.spring_boot_jpa_example.module.users;

import com.example.spring_boot_jpa_example._core.utils.APIUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // RestAPI로 요청하고 응답할 컨트롤러를 구현할때 사용되는 어노테이션.
@RequiredArgsConstructor // 해당 클래스의 final로 지정된 필드를 가지는 생성자를 자동으로 생성하는 어노테이션. 의존성 주입을 위해 사용됨.
@RequestMapping("/api/v1/users") // 클래스의 매핑될 url을 정의하는 어노테이션.
public class UsersRestController {
    private final UsersImpService usersImpService;

    @GetMapping
    public ResponseEntity<?> page(
            Pageable pageable // 페이징 요철할때 사용할 수 있는 객체입니다. 클라이언트가 요청할 때 쿼리스트링으로 ?page=2&size=20 등을 입력할 수 있습니다.
    ) {

        var responseDTO = usersImpService.page(pageable);

        return ResponseEntity.ok(APIUtils.success(responseDTO));
    }
}
