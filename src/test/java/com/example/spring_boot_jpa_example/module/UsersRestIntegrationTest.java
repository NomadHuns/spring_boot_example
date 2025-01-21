package com.example.spring_boot_jpa_example.module;

import com.example.spring_boot_jpa_example.module.users.dtos.UsersSaveRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional // 테스트 후 데이터 초기화하기 위한 애노테이션
@AutoConfigureMockMvc // MockMvc는 컨트롤러와 웹 계층을 테스트할 때 사용되며, 실제 서버를 시작하지 않고 요청/응답을 테스트할 수 있습니다. Spring 애플리케이션 컨텍스트에 필요한 모든 빈을 로드한 후, MockMvc를 테스트에 사용할 수 있도록 자동으로 설정합니다.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // Spring Boot 애플리케이션의 통합 테스트를 위한 기본 애노테이션입니다.
public class UsersRestIntegrationTest {
    @Autowired // Spring에서 의존성 주입(DI, Dependency Injection)을 자동으로 처리하는 애노테이션입니다. 테스트 클래스에서는 해당 애노테이션을 사용하여 DI 하여야 문제가 발생하지 않습니다.
    private MockMvc mvc; // HTTP 요청 및 응답을 시뮬레이션하여 컨트롤러 동작을 테스트할 수 있습니다.
    @Autowired
    private ObjectMapper om; // ObjectMapper는 Jackson 라이브러리에서 제공하는 JSON 직렬화 및 역직렬화 도구입니다.

    @Test // 이 메서드가 JUnit 테스트 메서드임을 나타냅니다.
    // Spring Security에서 제공하는 애노테이션으로, 테스트 실행 전에 지정된 사용자의 인증 정보를 미리 설정합니다. 인증된 사용자로 "tadmin"이라는 사용자 이름을 사용합니다.
    @WithUserDetails(value = "tadmin", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void page_success_test_1() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(
                get("/api/v1/users?size=1&page=0") // MockMvc를 사용하여 GET 요청을 /api/v1/users 엔드포인트로 보냅니다.
                        .contentType(MediaType.APPLICATION_JSON_VALUE) // application/json으로 설정하여 JSON 요청임을 명시합니다.
        );

        // 서버에서 반환된 응답의 본문(Content)을 문자열로 변환하여 출력합니다.
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().is2xxSuccessful()); // 응답 상태 코드가 2xx (성공 상태)인지 확인합니다.
        resultActions.andExpect(jsonPath("$.success").value(true)); // $.success: 응답 JSON의 루트 레벨에 있는 success 필드값이 true 인지 확인합니다.
    }

    @Test
    public void page_fail_test_1() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(
                get("/api/v1/users?size=1&page=0")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test // 이 메서드가 JUnit 테스트 메서드임을 나타냅니다.
    @WithUserDetails(value = "tmanager", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void page_fail_test_2() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(
                get("/api/v1/users?size=1&page=0")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // 서버에서 반환된 응답의 본문(Content)을 문자열로 변환하여 출력합니다.
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().is4xxClientError());
        resultActions.andExpect(jsonPath("$.success").value(false));
    }

    @Test // 이 메서드가 JUnit 테스트 메서드임을 나타냅니다.
    @WithUserDetails(value = "tadmin", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void detail_success_test_1() throws Exception {
        // given
        long id = 1L;

        // when
        ResultActions resultActions = mvc.perform(
                get("/api/v1/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response.user.id").value(id));
    }

    @Test
    @WithUserDetails(value = "tmanager", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void detail_fail_test_1() throws Exception {
        // given
        long id = 1L;

        // when
        ResultActions resultActions = mvc.perform(
                get("/api/v1/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().is4xxClientError());
        resultActions.andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @WithUserDetails(value = "tadmin", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void save_success_test_1() throws Exception {
        // given
        String username = "hello";
        String password = "1234";
        String email = "hello@gmail.com";

        var requestDTO = new UsersSaveRequestDTO(username, password, email);

        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post("/api/v1/users")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response.user.username").value(username));
        resultActions.andExpect(jsonPath("$.response.user.email").value(email));
    }

}
