package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.controller.config.TestClassUtill;
import back.springbootdeveloper.seungchan.domain.*;
import back.springbootdeveloper.seungchan.dto.request.AttendanceNumberRequest;
import back.springbootdeveloper.seungchan.dto.request.RequestUserForm;
import back.springbootdeveloper.seungchan.dto.request.VacationRequest;
import back.springbootdeveloper.seungchan.repository.*;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserUtilRepository userUtilRepository;
    @Autowired
    private AttendanceStatusRepository attendanceStatusRepository;
    @Autowired
    private NumOfTodayAttendenceRepository numOfTodayAttendenceRepository;

    private String token;
    private User user;
    private User userOb;
    private UserUtill userUtill;
    private AttendanceStatus attendanceStatus;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context) // MockMVB 설정
                .build();
    }

    //    token 발급
    @BeforeEach
    public void tokenSetUp() throws Exception {
        userRepository.deleteAll();
        userUtilRepository.deleteAll();
        attendanceStatusRepository.deleteAll();

        user = userRepository.save(TestClassUtill.makeUser());
        userRepository.updateId(user.getId(), 1L);
        user.setId(1L);

        userOb = userRepository.save(TestClassUtill.makeUserOb());
        userRepository.updateId(userOb.getId(), 2L);
        userOb.setId(2L);

        userUtill = userUtilRepository.save(TestClassUtill.makeUserUtill(user));

        attendanceStatus = attendanceStatusRepository.save(TestClassUtill.makeAttendanceStatus(user));

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
        this.suggestionRepository.deleteAll();
        final String url = "/suggestions/write";
        Suggestions suggestionsRequest = TestClassUtill.makeSuggestions();

        // 객체 suggestionsRequest을 Json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(suggestionsRequest);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isOk());

        List<Suggestions> suggestionsList = suggestionRepository.findAll();
        assertThat(suggestionsList.size()).isEqualTo(1);
        assertThat(suggestionsList.get(0).getClassification()).isEqualTo(suggestionsRequest.getClassification());
        assertThat(suggestionsList.get(0).getTitle()).isEqualTo(suggestionsRequest.getTitle());
        assertThat(suggestionsList.get(0).getHolidayPeriod()).isEqualTo(suggestionsRequest.getHolidayPeriod());

    }

    @DisplayName("main page의 현재 재학 인원들 조회")
    @Test
    public void findAllYbUserTest() throws Exception {
        // given
        final String url = "/main/ybs";

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cntVacation").value(userUtill.getCntVacation()))
                .andExpect(jsonPath("$[0].name").value(userUtill.getName()))
                .andExpect(jsonPath("$[0].weeklyData").value(attendanceStatus.getWeeklyData()));
    }

    @DisplayName("메인 회원상세(일반, 졸업자) 조회")
    @Test
    public void fetchUserOfDetail2MainTest() throws Exception {
        // given
        final String url = "/main/detail/{id}";

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, user.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );


        // then
        resultActions
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.major").value(user.getMajor()))
                .andExpect(jsonPath("$.studentId").value(user.getStudentId()))
                .andExpect(jsonPath("$.phoneNum").value(user.getPhoneNum()))
                .andExpect(jsonPath("$.hobby").value(user.getHobby()))
                .andExpect(jsonPath("$.specialtySkill").value(user.getSpecialtySkill()))
                .andExpect(jsonPath("$.mbti").value(user.getMbti()))
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.ob").value(user.isOb()))
                .andExpect(jsonPath("$.nuriKing").value(true));
    }

    @DisplayName("main page 졸업 인원들의 정보")
    @Test
    public void findAllObUserTest() throws Exception {
        // given
        final String url = "/main/obs";

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        resultActions
                .andExpect(jsonPath("$[0].obUserList[0].name").value(userOb.getName()))
                .andExpect(jsonPath("$[0].obUserList[0].yearOfRegistration").value(userOb.getYearOfRegistration()))
                .andExpect(jsonPath("$[0].obUserList[0].phoneNum").value(userOb.getPhoneNum()));
    }

    @DisplayName("출석 번호 입력 API 테스트")
    @Test
    public void attendanceNumberControllerTest() throws Exception {
        // given
        final String url = "/attendance/number";
        NumOfTodayAttendence numOfTodayAttendence = numOfTodayAttendenceRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("not found: "));
        String num = numOfTodayAttendence.getCheckNum();


        AttendanceNumberRequest attendanceNumberRequest = new AttendanceNumberRequest();
        attendanceNumberRequest.setNumOfAttendance(num);
        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(attendanceNumberRequest))
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        result
                .andExpect(jsonPath("$.passAtNow").value(true));
    }

    @DisplayName("내 정보를 조회한다.")
    @Test
    public void findMypageTest() throws Exception {
        // given
        final String url = "/mypage";

        // when
        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        result
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.phoneNum").value(user.getPhoneNum()))
                .andExpect(jsonPath("$.major").value(user.getMajor()))
                .andExpect(jsonPath("$.gpa").value(user.getGpa()))
                .andExpect(jsonPath("$.address").value(user.getAddress()))
                .andExpect(jsonPath("$.specialtySkill").value(user.getSpecialtySkill()))
                .andExpect(jsonPath("$.hobby").value(user.getHobby()))
                .andExpect(jsonPath("$.mbti").value(user.getMbti()))
                .andExpect(jsonPath("$.studentId").value(user.getStudentId()))
                .andExpect(jsonPath("$.birthDate").value(user.getBirthDate()))
                .andExpect(jsonPath("$.advantages").value(user.getAdvantages()))
                .andExpect(jsonPath("$.disadvantage").value(user.getDisadvantage()))
                .andExpect(jsonPath("$.selfIntroduction").value(user.getSelfIntroduction()))
                .andExpect(jsonPath("$.photo").value(user.getPhoto()))
                .andExpect(jsonPath("$.yearOfRegistration").value(user.getYearOfRegistration()))
                .andExpect(jsonPath("$.ob").value(user.isOb()));
    }

    @DisplayName("나의 정보를 업데이트 한다.")
    @Test
    public void updateMypageTest() throws Exception {
        // given
        final String url = "/mypage/update";
        final String nameUpdate = "업데이트한_이름";
        User updateUser = user;
        updateUser.setName(nameUpdate);
        RequestUserForm requestUserForm = new RequestUserForm(
                user.getName(),
                user.getPhoneNum(),
                user.getMajor(),
                user.getGpa(),
                user.getAddress(),
                user.getSpecialtySkill(),
                user.getHobby(),
                user.getMbti(),
                user.getStudentId(),
                user.getBirthDate(),
                user.getAdvantages(),
                user.getDisadvantage(),
                user.getSelfIntroduction(),
                user.getPhoto()
        );
        System.out.println("requestUserForm = " + requestUserForm);

        // 객체 suggestionsRequest을 Json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(requestUserForm);

        // when
        ResultActions result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        updateUser = userRepository.findById(user.getId())
                .orElseThrow(IllegalArgumentException::new);

        // then
        result
                .andExpect(status().isOk()); // 변경된 것이 잘되었는지 확인
        assertThat(updateUser.getName()).isEqualTo(nameUpdate);
        // then
    }

    @DisplayName("회원들이 휴가을 휴가 신청 API")
    @Test
    public void applyVacationTest() throws Exception {
        // given
        final String url = "/vacations/request";
        String preVacationDate[] = new String[]{"2023-07-28", "2023-07-30"};
        int cntUseOfVacation = 2;
        VacationRequest vacationRequest = new VacationRequest(preVacationDate, cntUseOfVacation);

        // 객체 suggestionsRequest을 Json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(vacationRequest);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUserId(user.getId());

        // then
        result
                .andExpect(status().isOk());
        assertThat(attendanceStatus.getVacationDates()).contains(preVacationDate);
    }
}
