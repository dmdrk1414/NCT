package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.filter.CustomHttpStatus;
import back.springbootdeveloper.seungchan.constant.filter.exception.ExceptionMessage;
import back.springbootdeveloper.seungchan.dto.request.FindPasswordReqDto;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.service.DatabaseService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import back.springbootdeveloper.seungchan.testutills.TestUtills;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest()
@AutoConfigureMockMvc
class LookupControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private TestSetUp testSetUp;
    @Autowired
    private UserService userService;
    private String token;
    private Long kingUserId;

    @BeforeEach
    void setUp() {
        databaseService.deleteAllDatabase();
        token = testSetUp.getToken(mockMvc);
        kingUserId = testSetUp.getKingUserId();
    }


    @DisplayName("임시_이메일_반환_테스트 : 반환 메세지, PW변경 확인")
    @Test
    void 임시_이메일_반환_테스트() throws Exception {
        // given
        UserInfo kingUser = userService.findUserById(kingUserId);
        final String url = "/admin/find/password";
        String beforePassword = kingUser.getPassword();
        FindPasswordReqDto reqestDto = FindPasswordReqDto.builder()
                .email(kingUser.getEmail())
                .name(kingUser.getName())
                .authenticationEmail(kingUser.getEmail())
                .build();

        // when
        final String requestBody = objectMapper.writeValueAsString(reqestDto);

        ResultActions result = mockMvc.perform(post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
        );

        String afterPassword = userService.findUserById(kingUserId).getPassword();

        // than
        result
                .andExpect(jsonPath("$.message").value(ResponseMessage.TEMP_PASSWORD_MESSAGE.get()))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

        assertThat(beforePassword).doesNotContain(afterPassword);
    }

    @DisplayName("임시_이메일_반환_테스트 : 유저가_존제하지_않는다")
    @ParameterizedTest
    @CsvSource({
            "NOTTHING@email,박승찬",
            "seungchan141414@gmail.com,NAME",
            "NOTTHING@email,NAME"
    })
    void 임시_이메일_반환_예외_유저가_존제하지_않는다_테스트(String email, String name) throws Exception {
        // given
        UserInfo kingUser = userService.findUserById(kingUserId);
        final String url = "/admin/find/password";
        FindPasswordReqDto requestDto = FindPasswordReqDto.builder()
                .email(email)
                .name(name)
                .authenticationEmail(kingUser.getEmail())
                .build();

        // when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
                        .header("authorization", "Bearer " + token) // token header에 담기
                )
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // JSON 응답을 Map으로 변환
        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);

        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(stateCode).isEqualTo(CustomHttpStatus.USER_NOT_EXIST.value());
    }
}