package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.controller.config.TestClassUtill;
import back.springbootdeveloper.seungchan.domain.Suggestions;
import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.domain.UserUtill;
import back.springbootdeveloper.seungchan.repository.SuggestionRepository;
import back.springbootdeveloper.seungchan.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// 메인 애플리케이션 클래스에 추가하는 애너테이션인 @SpringBootApplication이 있는 클래스 찾고
// 클래스에 포함되어 있는 빈을 찾은 다음, 테스트용 애플리케이션 컨텍스트라는 것을 만든다.
@SpringBootTest

// @AutoConfigureMockMvc는 MockMvc를 생성, 자동으로 구성하는 애너테이션
// MockMvc는 어플리케이션을 서버에 배포하지 하지 않고 테스트용 MVC 환경을 만들어 요청 및 전송, 응갇기능을 제공하는 유틸리티 클래스
// 컨트롤러를 테스트를 할때 사용되는 클래스
@AutoConfigureMockMvc // MockMvc 생성
public class ApiTest {
    // MockMVC 메서드 설명
    // perform() : 메서드는 요청을 전송하는 역할을 하는 메서드
    //              반환은 ResultActions 객체를 받으며
    //              ResultActions 객체는 반환값을 검증하고 확인한는 andExpect() 메서드를 제공

    // accept() : 메서드는 요청을 보낼 때 무슨 타입으로 응답을 받을지 결정하는 메서드
    //              JSON, XML 등 다양한 타입이 있지만, JSON을 받는다고 명시해둔다.

    // jsonPath("$[0].${필드명}) : JSON 응답값의 값을 가져오는 역할을 하는 메서드
    //                           0번째 배열에 들어있는 객체의 id, name값을 가져온다
    // ------------------------------------------------------------------

    // MockMvc 생성, MockMvc는 애플리케이션을 서버에 배포하지 않고, 테스트용 MVB 환경을 만들어 요청 및 전송, 응답 기능을 제공하는것
    // 컨트롤러를 테스트할 때 사용되는 클래스
    @Autowired
    protected MockMvc mockMvc;
    // ObjectMapper 클래스 - 직렬화, 역직렬화 할때 사용
    // 자바 객체를 JSON 데이터로 변환 OR JSON 데이터를 자바 객체로 변환
    // 직렬화 : 자바 시스템 내부에서 사용하는 객체를 외부에서 사용하도록 데이터를 변환하는 작업
    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스
    @Autowired
    private SuggestionRepository suggestionRepository;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TokenService tokenService;

    private String token;
    private User user;
    private UserUtill userUtill;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context) // MockMVB 설정
                .build();
    }

    //    token 발급
    @BeforeEach
    public void tokenSetUp() throws Exception {
        user = TestClassUtill.makeUser();
        userUtill = TestClassUtill.makeUserUtill(user);

        String url = "/login";
        HttpServletRequest request = mockMvc.perform(
                post(url).param("email", user.getEmail()).param("password", user.getPassword())
        ).andReturn().getRequest();

        HttpServletResponse response = mockMvc.perform(
                post(url).param("email", user.getEmail()).param("password", user.getPassword())
        ).andReturn().getResponse();

        token = tokenService.createAccessAndRefreshToken(request, response, user.getEmail());
    }

    @DisplayName("건의 게시판 전체  조회 테스트")
    @Test
    public void fetchSuggestionsTest() throws Exception {
        // given
        this.suggestionRepository.deleteAll();

        final String url = "/suggestions";

        Suggestions saveSuggestions = suggestionRepository.save(TestClassUtill.makeSuggestions());
        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token)); // token header에 담기

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.suggestionLists[0].id").value(saveSuggestions.getId()))
                .andExpect(jsonPath("$.suggestionLists[0].classification").value(saveSuggestions.getClassification()))
                .andExpect(jsonPath("$.suggestionLists[0].title").value(saveSuggestions.getTitle()))
                .andExpect(jsonPath("$.suggestionLists[0].holidayPeriod").value(saveSuggestions.getHolidayPeriod()))
                .andExpect(jsonPath("$.suggestionLists[0].check").value(false))
                .andExpect(jsonPath("$.nuriKing").value(userUtill.isNuriKing()));
    }

    @DisplayName("건의 게시판 작성 테스트")
    @Test
    public void writeSuggestionTest() throws Exception {
        // given


        // when


        // then

    }
}
