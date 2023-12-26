package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.Constant.exception.ExceptionMessage;
import back.springbootdeveloper.seungchan.dto.request.UserLoginRequest;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.service.DatabaseService;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import back.springbootdeveloper.seungchan.testutills.TestUtills;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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

@TestPropertySource(locations = "classpath:/messages/validation.properties")
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
    private String test;

    @BeforeEach
    public void setUp() {
        databaseService.deleteAllDatabase();
        this.kingUser = testSetUp.setUpKingUser();
    }

    @ParameterizedTest
    @CsvSource({"null,1234", "seungchan@gmail.com,null", "null,null"})
    void 유저_로그인_테스트_예외_테스트_값이_없는_경우_1(String email, String password) throws Exception {
        if (email.equals("null")) {
            email = null;
        }

        if (password.equals("null")) {
            password = null;
        }

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

        assertThat(message).isEqualTo(test);
        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}