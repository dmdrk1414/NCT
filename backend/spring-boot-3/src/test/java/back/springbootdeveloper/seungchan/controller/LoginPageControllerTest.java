package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.UserLoginRequest;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.service.DatabaseService;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import back.springbootdeveloper.seungchan.testutills.TestUtills;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@TestPropertySource(locations = "classpath:/messages/validation/validation.properties")
@SpringBootTest()
@AutoConfigureMockMvc
class LoginPageControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private TestSetUp testSetUp;
    private UserInfo kingUser;
    @Value("${email.notnull}")
    private String MESSAGE_EMAIL_NOT_NULL;
    @Value("${password.notnull}")
    private String MESSAGE_PASSWORD_NOT_NULL;
    @Value("${email.invalid}")
    private String MESSAGE_EMAIL_INVALID;

    @BeforeEach
    public void setUp() {
        databaseService.deleteAllDatabase();
        this.kingUser = testSetUp.setUpKingUser();
    }

    @ParameterizedTest
    @NullSource
    void 유저_로그인_테스트_예외_테스트_email_값이_없는_경우_1(String email) throws Exception {
        String password = kingUser.getPassword();

        // given
        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        final String url = "/login";

        // when
        final String requestBody = objectMapper.writeValueAsString(userLoginRequest);

        MvcResult result = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // JSON 응답을 Map으로 변환
        String message = TestUtills.getMessageFromResponse(response);
        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);

        assertThat(message).isEqualTo(MESSAGE_EMAIL_NOT_NULL);
        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest
    @NullSource
    void 유저_로그인_테스트_예외_테스트_password_값이_없는_경우(String password) throws Exception {
        String email = kingUser.getEmail();

        // given
        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        final String url = "/login";

        // when
        final String requestBody = objectMapper.writeValueAsString(userLoginRequest);

        MvcResult result = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // JSON 응답을 Map으로 변환
        String message = TestUtills.getMessageFromResponse(response);
        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);

        assertThat(message).isEqualTo(MESSAGE_PASSWORD_NOT_NULL);
        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "asdf",
            "12  aea3",
            "asdf sadf asdfdsa",
            "sadf@sdaf@sdf",
            "23!saf",
            "invalid_email",
            "user@.com",
            "@example.com",
            "user@.com.",
            "user@example..com",
            "user@exa mple.com",
            "user@example.com.",
            "user@.example.com",
            "user@-example.com",
            "user@example-.com",
            "user@example.com-",
            "user@exam@ple.com"
    })
    void 유저_로그인_테스트_예외_테스트_형식에_맞지_않는_이메일(String email) throws Exception {
        String password = kingUser.getPassword();

        // given
        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        final String url = "/login";

        // when
        final String requestBody = objectMapper.writeValueAsString(userLoginRequest);

        MvcResult result = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // JSON 응답을 Map으로 변환
        String message = TestUtills.getMessageFromResponse(response);
        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);

        assertThat(message).isEqualTo(MESSAGE_EMAIL_INVALID);
        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}