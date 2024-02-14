package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.filter.CustomHttpStatus;
import back.springbootdeveloper.seungchan.dto.request.TeamArticleUpdateReqDto;
import back.springbootdeveloper.seungchan.entity.TeamArticle;
import back.springbootdeveloper.seungchan.entity.TeamArticleComment;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.TeamArticleRepository;
import back.springbootdeveloper.seungchan.service.DatabaseService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import back.springbootdeveloper.seungchan.testutills.TestUtills;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@TestPropertySource(locations = "classpath:/messages/validation/validation.properties")
@SpringBootTest()
@AutoConfigureMockMvc
class TeamArticleControllerTest {
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
    private UserInfo kingUser;
    private TeamArticle teamArticle;

    @Autowired
    private TeamArticleRepository teamArticleRepository;

    @Value("${validation.team.article.title.notblank}")
    private String MESSAGE_VALID_TITLE;
    @Value("${validation.team.article.content.notblank}")
    private String MESSAGE_VALID_CONTENT;

    @BeforeEach
    void setUp() {
        databaseService.deleteAllDatabase();
        token = testSetUp.getToken(mockMvc);
        kingUserId = testSetUp.getKingUserId();
        kingUser = userService.findUserById(kingUserId);

        teamArticleRepository.deleteAll();
    }

    @Test
    void Team_Article_제목_내용_수정_테스트() throws Exception {
        // given
        saveArticle();
        final String URL = "/clubs/informations/articles/{article_id}";
        String titleUpdatle = "update test title";
        String contentUpdate = "update test content";

        TeamArticleUpdateReqDto requestDto = TeamArticleUpdateReqDto.builder()
                .clubArticleUpdateTitle(titleUpdatle)
                .clubArticleUpdateCotent(contentUpdate)
                .build();

        // when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions result = mockMvc.perform(put(URL, teamArticle.getTeamArticleId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // than
        result
                .andExpect(jsonPath("$.message").value(ResponseMessage.UPDATE_TEAM_ARTICLE_MESSAGE.get()))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

        // 실제로 DB에 값이 변경이 되었는지 확인
        TeamArticle target = teamArticleRepository.findById(teamArticle.getTeamArticleId()).orElseThrow(EntityNotFoundException::new);

        // jUnit 5버전의 테스트코드를 사용
        assertThat(target.getTitle()).isEqualTo(titleUpdatle);
        assertThat(target.getContent()).isEqualTo(contentUpdate);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "title_1",
            "title_2"
    })
    void Team_Article_제목_빈값_NULL_테스트(String check) throws Exception {
        System.out.println("check = " + check);
        // given
        saveArticle();
        final String URL = "/clubs/informations/articles/{article_id}";
        String titleUpdatle = "update test title";
        String contentUpdate = "update test content";

        TeamArticleUpdateReqDto requestDto = TeamArticleUpdateReqDto.builder()
                .clubArticleUpdateTitle(titleUpdatle)
                .clubArticleUpdateCotent(contentUpdate)
                .build();

        switch (check) {
            case "title_1":
                requestDto.setClubArticleUpdateTitle("");
                break;
            case "title_2":
                requestDto.setClubArticleUpdateTitle(null);
                break;
        }
        // when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put(URL, teamArticle.getTeamArticleId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
                .header("authorization", "Bearer " + token) // token header에 담기
        ).andReturn();

        // than
        MockHttpServletResponse response = result.getResponse();

        // JSON 응답을 Map으로 변환
        String message = TestUtills.getMessageFromResponse(response);
        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);

        assertThat(message).isEqualTo(MESSAGE_VALID_TITLE);
        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "content_1",
            "content_2"
    })
    void Team_Article_내용_빈값_NULL_테스트(String check) throws Exception {
        System.out.println("check = " + check);
        // given
        saveArticle();
        final String URL = "/clubs/informations/articles/{article_id}";
        String titleUpdatle = "update test title";
        String contentUpdate = "update test content";

        TeamArticleUpdateReqDto requestDto = TeamArticleUpdateReqDto.builder()
                .clubArticleUpdateTitle(titleUpdatle)
                .clubArticleUpdateCotent(contentUpdate)
                .build();

        switch (check) {
            case "content_1":
                requestDto.setClubArticleUpdateCotent("");
                break;
            case "content_2":
                requestDto.setClubArticleUpdateCotent(null);
                break;
        }
        // when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put(URL, teamArticle.getTeamArticleId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
                .header("authorization", "Bearer " + token) // token header에 담기
        ).andReturn();

        // than
        MockHttpServletResponse response = result.getResponse();

        // JSON 응답을 Map으로 변환
        String message = TestUtills.getMessageFromResponse(response);
        HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);
        Integer stateCode = TestUtills.getCustomHttpStatusCodeFromResponse(response);

        assertThat(message).isEqualTo(MESSAGE_VALID_CONTENT);
        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
    }

    void saveArticle() {
        // TeamArticle - TeamArticleClassfication
        //             \- TeamArticleComment
        String title = "test title";
        String content = "test content";


        // 2.TeamArticleComment
        TeamArticleComment teamArticleComment_1 = TeamArticleComment.builder()
                .content("test Team Article Comment_1")
                .build();
        TeamArticleComment teamArticleComment_2 = TeamArticleComment.builder()
                .content("test Team Article Comment_2")
                .build();
        TeamArticleComment teamArticleComment_3 = TeamArticleComment.builder()
                .content("test Team Article Comment_3")
                .build();

        //        // JPA 영속성
        teamArticle = teamArticleRepository.save(
                TeamArticle.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        teamArticle.addTeamArticleComment(teamArticleComment_1);
        teamArticle.addTeamArticleComment(teamArticleComment_2);
        teamArticle.addTeamArticleComment(teamArticleComment_3);

        teamArticleRepository.save(teamArticle);
    }
}