package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.SuggestionConstant;
import back.springbootdeveloper.seungchan.constant.filter.CustomHttpStatus;
import back.springbootdeveloper.seungchan.constant.filter.exception.ExceptionMessage;
import back.springbootdeveloper.seungchan.dto.request.SuggestionWriteReqDto;
import back.springbootdeveloper.seungchan.entity.Suggestions;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.service.DatabaseService;
import back.springbootdeveloper.seungchan.service.SuggestionService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import back.springbootdeveloper.seungchan.testutills.TestUtills;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest()
@AutoConfigureMockMvc
class SuggestionsControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private TestSetUp testSetUp;
    @Autowired
    private SuggestionService suggestionService;
    @Autowired
    private UserService userService;
    private String token;
    private Long kingUserId;
    private UserInfo kingUser;

    @BeforeEach
    void setUp() {
        databaseService.deleteAllDatabase();
        token = testSetUp.getToken(mockMvc);
        kingUserId = testSetUp.getKingUserId();
        kingUser = userService.findUserById(kingUserId);
    }

    @Test
    void 건의_게시판_작성_테스트() throws Exception {
        // given
        final String url = "/suggestion/write";

        String title = "test title";
        String classification = SuggestionConstant.SUGGESTION.getClassification();
        String holidayPeriod = "";

        SuggestionWriteReqDto requestDto = SuggestionWriteReqDto.builder()
                .title(title)
                .classification(classification)
                .holidayPeriod(holidayPeriod)
                .build();

        // when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions result = mockMvc.perform(post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        List<Suggestions> suggestions = suggestionService.findAll();

        assertThat(suggestions).extracting("classification").contains(classification);
        assertThat(suggestions).extracting("title").contains(title);

        // than
        result
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
    }

    @ParameterizedTest
    @ValueSource(strings =
            {
                    "q",
                    "!",
                    "건 의",
                    "휴 가",
                    "비 밀",
                    "자 유",
                    "123"
            }
    )
    void 건의_게시판_작성_잘못된_분류_예외_테스트(String input) throws Exception {
        // given
        final String url = "/suggestion/write";
        String title = "test title";
        String holidayPeriod = "";

        SuggestionWriteReqDto requestDto = SuggestionWriteReqDto.builder()
                .title(title)
                .classification(input)
                .holidayPeriod(holidayPeriod)
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
        String message = TestUtills.getMessageFromResponse(response);
        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);

        assertThat(message).isEqualTo(ExceptionMessage.INVALID_SELECTION_CLASSIFICATION.get());
        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(stateCode).isEqualTo(CustomHttpStatus.INVALID_SELECTION_CLASSIFICATION.value());

    }

    @ParameterizedTest
    @ValueSource(strings =
            {
                    "classification",
                    "title"
            }
    )
    void 건의_게시판_작성_공백_예외_테스트(String input) throws Exception {
        // given
        final String url = "/suggestion/write";
        String title = "test title";
        String holidayPeriod = "";
        String classification = SuggestionConstant.SUGGESTION.getClassification();

        SuggestionWriteReqDto requestDto = null;
        if (input.contains("classification")) {
            requestDto = SuggestionWriteReqDto.builder()
                    .title(title)
                    .classification("")
                    .holidayPeriod(holidayPeriod)
                    .build();
        }

        if (input.contains("title")) {
            requestDto = SuggestionWriteReqDto.builder()
                    .title("")
                    .classification(classification)
                    .holidayPeriod(holidayPeriod)
                    .build();
        }

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
        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
    }
}