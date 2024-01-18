package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.SuggestionConstant;
import back.springbootdeveloper.seungchan.constant.filter.CustomHttpStatus;
import back.springbootdeveloper.seungchan.constant.filter.exception.ExceptionMessage;
import back.springbootdeveloper.seungchan.dto.request.SuggestionReqDto;
import back.springbootdeveloper.seungchan.dto.request.SuggestionWriteReqDto;
import back.springbootdeveloper.seungchan.entity.Suggestion;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.service.DatabaseService;
import back.springbootdeveloper.seungchan.service.SuggestionService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import back.springbootdeveloper.seungchan.testutills.TestUtills;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        List<Suggestion> suggestions = suggestionService.findAll();

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

    @Test
    void 건의_게시판_조회_테스트() throws Exception {
        // given
        final String url = "/suggestion";
        List<Suggestion> targets = List.of(testSetUp.saveSuggestion(), testSetUp.saveSuggestion(), testSetUp.saveSuggestion());

        // when
        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        int one = 0;
        int two = 1;
        int three = 2;
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.suggestionLists[0].id").value(targets.get(one).getId()))
                .andExpect(jsonPath("$.suggestionLists[0].classification").value(targets.get(one).getClassification()))
                .andExpect(jsonPath("$.suggestionLists[0].title").value(targets.get(one).getTitle()))
                .andExpect(jsonPath("$.suggestionLists[0].holidayPeriod").value(targets.get(one).getHolidayPeriod()))
                .andExpect(jsonPath("$.suggestionLists[0].check").value(false))

                .andExpect(jsonPath("$.suggestionLists[1].id").value(targets.get(two).getId()))
                .andExpect(jsonPath("$.suggestionLists[1].classification").value(targets.get(two).getClassification()))
                .andExpect(jsonPath("$.suggestionLists[1].title").value(targets.get(two).getTitle()))
                .andExpect(jsonPath("$.suggestionLists[1].holidayPeriod").value(targets.get(two).getHolidayPeriod()))
                .andExpect(jsonPath("$.suggestionLists[1].check").value(false))

                .andExpect(jsonPath("$.suggestionLists[2].id").value(targets.get(three).getId()))
                .andExpect(jsonPath("$.suggestionLists[2].classification").value(targets.get(three).getClassification()))
                .andExpect(jsonPath("$.suggestionLists[2].title").value(targets.get(three).getTitle()))
                .andExpect(jsonPath("$.suggestionLists[2].holidayPeriod").value(targets.get(three).getHolidayPeriod()))
                .andExpect(jsonPath("$.suggestionLists[2].check").value(false));
    }

    @Test
    void 각각_건의_게시판_조회_테스트() throws Exception {
        // given
        final String url = "/suggestion/{id}";
        Suggestion target = testSetUp.saveSuggestion();
        // when
        ResultActions result = mockMvc.perform(get(url, target.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(target.getId()))
                .andExpect(jsonPath("$.classification").value(target.getClassification()))
                .andExpect(jsonPath("$.title").value(target.getTitle()))
                .andExpect(jsonPath("$.holidayPeriod").value(target.getHolidayPeriod()))
                .andExpect(jsonPath("$.check").value(false));
    }

    @Test
    void 건의_게시판_확인_토글_테스트() throws Exception {
        // given
        final String url = "/suggestion/check";
        Suggestion target = testSetUp.saveSuggestion();

        SuggestionReqDto requestDto = SuggestionReqDto.builder()
                .suggestionId(target.getId())
                .build();

        // when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions result = mockMvc.perform(post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // than
        result
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
    }
}