package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.Constant.filter.exception.ExceptionMessage;
import back.springbootdeveloper.seungchan.dto.request.TempUserFormReqDto;
import back.springbootdeveloper.seungchan.dto.request.UserLoginRequest;
import back.springbootdeveloper.seungchan.entity.TempUser;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.repository.TempUserRepository;
import back.springbootdeveloper.seungchan.repository.UserRepository;
import back.springbootdeveloper.seungchan.service.DatabaseService;
import back.springbootdeveloper.seungchan.testutills.TestMakeObject;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import back.springbootdeveloper.seungchan.testutills.TestUtills;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

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
    @Autowired
    private TempUserRepository tempUserRepository;
    private UserInfo kingUser;
    @Value("${validation.email.notblank}")
    private String MESSAGE_EMAIL_NOT_NULL;
    @Value("${validation.password.notblank}")
    private String MESSAGE_PASSWORD_NOT_NULL;
    @Value("${validation.email.invalid}")
    private String MESSAGE_EMAIL_INVALID;

    @BeforeEach
    public void setUp() {
        databaseService.deleteAllDatabase();
        this.kingUser = testSetUp.setUpKingUser();
    }

    @Test
    void 유저_로그인_테스트() throws Exception {
        // when
        String email = kingUser.getEmail();
        String password = "1234";

        // given
        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        final String url = "/login";

        // when
        final String requestBody = objectMapper.writeValueAsString(userLoginRequest);

        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        result
                .andExpect(jsonPath("$.name").value(kingUser.getName()))
                .andExpect(jsonPath("$.userId").value(kingUser.getId()))
                .andExpect(jsonPath("$.nuriKing").value(true));
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

    @Test
    void 유저_로그인_테스트_예외_테스트_해당_유저가_없는_경우() throws Exception {
        String email = "NOTING@gmail.com";
        String password = "NOTING_PASSWORD";

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

        assertThat(message).isEqualTo(ExceptionMessage.USER_NOT_EXIST_MESSAGE.get());
        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void 신입유저를_등록을_확인_하는_테스트() throws Exception {
        // given
        String name = "신입_1";
        String email = "test1@test.com";
        String password = "testuser1!";
        TempUser tempUser = TestMakeObject.makeNewUserOb(email, name);
        final String url = "/sign";

        assertThat(tempUserRepository.count()).isEqualTo(0);

        TempUserFormReqDto request = new TempUserFormReqDto(
                tempUser.getName(),
                tempUser.getPhoneNum(),
                tempUser.getMajor(),
                tempUser.getGpa(),
                tempUser.getAddress(),
                tempUser.getSpecialtySkill(),
                tempUser.getHobby(),
                tempUser.getMbti(),
                tempUser.getStudentId(),
                tempUser.getBirthDate(),
                tempUser.getAdvantages(),
                tempUser.getDisadvantage(),
                tempUser.getSelfIntroduction(),
                tempUser.getPhoto(),
                tempUser.getEmail(),
                password
        );

        // when
        final String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        TempUser tempUserOfDb = tempUserRepository.findByEmail(email).get();

        // then
        assertThat(tempUserRepository.count()).isEqualTo(1);
        assertThat(tempUser.getEmail()).isEqualTo(email);
        assertThat(tempUser.getName()).isEqualTo(tempUserOfDb.getName());
    }
}