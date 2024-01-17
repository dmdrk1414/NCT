package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.SuggestionConstant;
import back.springbootdeveloper.seungchan.dto.request.SuggestionWriteRequest;
import back.springbootdeveloper.seungchan.entity.Suggestions;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.service.DatabaseService;
import back.springbootdeveloper.seungchan.service.SuggestionService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

        SuggestionWriteRequest requestDto = SuggestionWriteRequest.builder()
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
}