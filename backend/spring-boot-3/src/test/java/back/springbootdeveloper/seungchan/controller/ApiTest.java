package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.controller.config.AttendanceListFromJson;
import back.springbootdeveloper.seungchan.controller.config.TestMakeObject;
import back.springbootdeveloper.seungchan.dto.request.*;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.repository.*;
import back.springbootdeveloper.seungchan.service.TempUserService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    // service
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private TempUserService tempUserService;

    // Repository
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserUtilRepository userUtilRepository;
    @Autowired
    private AttendanceStatusRepository attendanceStatusRepository;
    @Autowired
    private NumOfTodayAttendenceRepository numOfTodayAttendenceRepository;
    @Autowired
    private TempUserRepository tempUserRepository;
    @Autowired
    private AttendanceTimeRepository attendanceTimeRepository;


    private String token;
    private UserInfo user;
    private UserInfo user_3;
    private UserInfo user_5;
    private UserInfo userOb;
    private UserInfo userOb_4;
    private UserUtill userUtill;
    private UserUtill userUtill_3;
    private UserUtill userUtill_5;
    private AttendanceStatus attendanceStatus;
    private AttendanceStatus attendanceStatus_3;
    private AttendanceStatus attendanceStatus_5;

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
        tempUserRepository.deleteAll();

        // userID 1
        Long userId_1 = 1L;
        user = userRepository.save(TestMakeObject.makeUser("박승찬", "seungchan141414@gmail.com"));
        userRepository.updateId(user.getId(), userId_1);
        user.setId(userId_1);

        // userId OD 2
        Long userId_2 = 2L;
        userOb = userRepository.save(TestMakeObject.makeUserOb("이승훈", "2@gmail.com"));
        userRepository.updateId(userOb.getId(), userId_2);
        userOb.setId(userId_2);

        userUtill = userUtilRepository.save(TestMakeObject.makeUserUtill(user, 0, true));

        String vacationDates = "2023-08-01, 2023-08-07, 2023-08-14";
        String absenceDates = "2023-08-15";
        String weeklyData = "[0,0,0,0,0]";
        attendanceStatus = attendanceStatusRepository.save(TestMakeObject.makeAttendanceStatus(user, vacationDates, absenceDates, weeklyData));

        String url = "/logn";
        HttpServletRequest request = mockMvc.perform(
                post(url).param("email", user.getEmail()).param("password", user.getPassword())
        ).andReturn().getRequest();

        HttpServletResponse response = mockMvc.perform(
                post(url).param("email", user.getEmail()).param("password", user.getPassword())
        ).andReturn().getResponse();

        token = tokenService.createAccessAndRefreshToken(request, response, user.getEmail());
    }

    @DisplayName("오성훈 ㅅㅂ 테스트")
    @Test
    public void 오성훈을위한테스트함수() throws Exception {
        userRepository.deleteAll();
        userUtilRepository.deleteAll();
        attendanceStatusRepository.deleteAll();
        tempUserRepository.deleteAll();

        // userID 1 3 5
        Long userId_1 = 1L;
        user = userRepository.save(TestMakeObject.makeUser("박승찬", "seungchan141414@gmail.com"));
        userRepository.updateId(user.getId(), userId_1);
        user.setId(userId_1);

        Long userId_3 = 3L;
        user_3 = userRepository.save(TestMakeObject.makeUser("김주연", "3@gmail.com"));
        userRepository.updateId(user_3.getId(), userId_3);
        user_3.setId(userId_3);

        Long userId_5 = 5L;
        user_5 = userRepository.save(TestMakeObject.makeUser("허진범", "5@gmail.com"));
        userRepository.updateId(user_5.getId(), userId_5);
        user_5.setId(userId_5);

        // userId OD 2 4
        Long userId_2 = 2L;
        userOb = userRepository.save(TestMakeObject.makeUserOb("이승훈", "2@gmail.com"));
        userRepository.updateId(userOb.getId(), userId_2);
        userOb.setId(userId_2);

        Long userId_4 = 4L;
        userOb_4 = userRepository.save(TestMakeObject.makeUserOb("이동근", "4@gmail.com"));
        userRepository.updateId(userOb_4.getId(), userId_4);
        userOb_4.setId(userId_4);

        userUtill = userUtilRepository.save(TestMakeObject.makeUserUtill(user, 0, true));
        userUtill_3 = userUtilRepository.save(TestMakeObject.makeUserUtill(user_3, 1, false));
        userUtill_5 = userUtilRepository.save(TestMakeObject.makeUserUtill(user_5, 1, false));

        String vacationDates = "2023-08-01, 2023-08-07, 2023-08-14";
        String absenceDates = "2023-08-15";
        String weeklyData = "[0,0,0,0,0]";
        attendanceStatus = attendanceStatusRepository.save(TestMakeObject.makeAttendanceStatus(user, vacationDates, absenceDates, weeklyData));

        String vacationDates_3 = "2023-08-08, 2023-08-18";
        String absenceDates_3 = "2023-08-11";
        String weeklyData_3 = "[1,1,1,0,0]";
        attendanceStatus_3 = attendanceStatusRepository.save(TestMakeObject.makeAttendanceStatus(user_3, vacationDates_3, absenceDates_3, weeklyData_3));

        String vacationDates_5 = " 2023-08-07, 2023-08-11";
        String absenceDates_5 = "2023-08-15";
        String weeklyData_5 = "[1,1,0,-1,1]";
        attendanceStatus_5 = attendanceStatusRepository.save(TestMakeObject.makeAttendanceStatus(user_5, vacationDates_5, absenceDates_5, weeklyData_5));

    }

    @DisplayName("건의 게시판 전체  조회 테스트")
    @Test
    public void fetchSuggestionsTest() throws Exception {
        // given
        this.suggestionRepository.deleteAll();

        final String url = "/suggestions";

        Suggestions saveSuggestions = suggestionRepository.save(TestMakeObject.makeSuggestions());
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
        Suggestions suggestionsRequest = TestMakeObject.makeSuggestions();

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

        String inputWeeklyData = attendanceStatus.getWeeklyData();
        List<Integer> weeklyDataList = new ArrayList<>();

        // 정규 표현식을 사용하여 숫자 추출
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(inputWeeklyData);

        while (matcher.find()) {
            // 추출된 문자열을 Integer로 변환하여 리스트에 추가
            weeklyDataList.add(Integer.parseInt(matcher.group()));
        }

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ybUserInfomationList[0].cntVacation").value(userUtill.getCntVacation()))
                .andExpect(jsonPath("$.ybUserInfomationList[0].name").value(userUtill.getName()))
                .andExpect(jsonPath("$.ybUserInfomationList[0].weeklyData").value(weeklyDataList))
                .andExpect(jsonPath("$.passAttendanceOfSearchUse").value(false));

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
    public void AttendanceIsPassController() throws Exception {
        // given
        final String url = "/attendance/number";
        List<NumOfTodayAttendence> numOfTodayAttendenceList = numOfTodayAttendenceRepository.findAll();
        NumOfTodayAttendence numOfTodayAttendence = numOfTodayAttendenceList.get(numOfTodayAttendenceList.size() - 1);
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

    @DisplayName("출석 번호 입력 API 테스트")
    @Test
    public void 출석_번호_입력__출석_결석_휴가를_이미_한상태에서의_출석_예외_1() throws Exception {
        // given
        final String url = "/attendance/number";
        List<NumOfTodayAttendence> numOfTodayAttendenceList = numOfTodayAttendenceRepository.findAll();
        NumOfTodayAttendence numOfTodayAttendence = numOfTodayAttendenceList.get(numOfTodayAttendenceList.size() - 1);
        String num = numOfTodayAttendence.getCheckNum();


        AttendanceNumberRequest attendanceNumberRequest = new AttendanceNumberRequest();
        attendanceNumberRequest.setNumOfAttendance(num);
        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(attendanceNumberRequest))
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        ResultActions result_2 = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(attendanceNumberRequest))
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        result_2
                .andExpect(jsonPath("$.passAtNow").value(false));
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
        UserInfo updateUser = user;
        updateUser.setName(nameUpdate);
        UpdateUserFormRequest requestUserForm = new UpdateUserFormRequest(
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
                user.getPhoto(),
                user.getEmail()
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

    @DisplayName("회원들의 휴가 날짜 조회 테스트")
    @Test
    public void findVacationTest() throws Exception {
        // given
        final String url = "/vacations";

        MockHttpServletResponse response = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        ).andReturn().getResponse();
        String responseStr = response.getContentAsString();

        // when
        ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        AttendanceListFromJson attendanceListFromJson = objectMapper.readValue(responseStr, AttendanceListFromJson.class);
        // then
        resultActions
                .andExpect(jsonPath("$.absences").value(attendanceListFromJson.getAbsences()))
                .andExpect(jsonPath("$.beforeVacationDate").value(attendanceListFromJson.getBeforeVacationDate()))
                .andExpect(jsonPath("$.preVacationDate").value(attendanceListFromJson.getPreVacationDate())
                );

    }

    @DisplayName("실장이 회원들에게 휴가 갯수 부여가능")
    @Test
    public void vacationCountTest() throws Exception {
        // given
        final String url = "/vacations/count";
        int cntVacation = userUtill.getCntVacation();
        int addCntVacation = 1;
        VacationCountRequest vacationCountRequest = new VacationCountRequest(addCntVacation, user.getId());
        int cntVacationOfAfterAdd = cntVacation + addCntVacation;

        // when
        // 객체 suggestionsRequest을 Json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(vacationCountRequest);

        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        userUtill = userUtilRepository.findByUserId(user.getId());

        // then
        result.andExpect(status().isOk());
        assertThat(userUtill.getCntVacation()).isEqualTo(cntVacationOfAfterAdd);
    }

    @DisplayName("휴가 신청 페이지 조회")
    @Test
    public void findsVacationRequestTest() throws Exception {
        // given
        final String url = "/vacations/request";

        MockHttpServletResponse response = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        ).andReturn().getResponse();
        String responseStr = response.getContentAsString();
        int cntVacation = userUtill.getCntVacation();

        // when
        ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        AttendanceListFromJson attendanceListFromJson = objectMapper.readValue(responseStr, AttendanceListFromJson.class);
        // then
        resultActions
                .andExpect(jsonPath("$.absences").value(attendanceListFromJson.getAbsences()))
                .andExpect(jsonPath("$.beforeVacationDate").value(attendanceListFromJson.getBeforeVacationDate()))
                .andExpect(jsonPath("$.preVacationDate").value(attendanceListFromJson.getPreVacationDate()))
                .andExpect(jsonPath("$.cntVacation").value(cntVacation));
    }

    @DisplayName("새로운 회원들의 회원가입 절차")
    @Test
    public void newUserSignUpTest() throws Exception {
        // given
        final String url = "/sign";
        String email = "new@new.com";
        String name = "새로운 유저";
        TempUser newUser = TestMakeObject.makeNewUserOb(email, name);

        TempUserFormRequest requestUserForm = new TempUserFormRequest(
                newUser.getName(),
                newUser.getPhoneNum(),
                newUser.getMajor(),
                newUser.getGpa(),
                newUser.getAddress(),
                newUser.getSpecialtySkill(),
                newUser.getHobby(),
                newUser.getMbti(),
                newUser.getStudentId(),
                newUser.getBirthDate(),
                newUser.getAdvantages(),
                newUser.getDisadvantage(),
                newUser.getSelfIntroduction(),
                newUser.getPhoto(),
                newUser.getEmail(),
                newUser.getPassword()
        );

        // 객체 suggestionsRequest을 Json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(requestUserForm);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        TempUser newUserOfTempDb = tempUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found: ")); // 찾아서 없으면 예외처리.;;

        boolean resultPassword = new BCryptPasswordEncoder().matches(name, newUserOfTempDb.getPassword());

        // then
        assertThat(newUser.getEmail()).isEqualTo(email);
        assertThat(resultPassword).isTrue();
        assertThat(newUser.getName()).isEqualTo(newUserOfTempDb.getName());

        // then
        result
                .andExpect(status().isOk());
    }

    @DisplayName("모든 신청 유저들의 정보를 볼수 있는 API 테스트")
    @Test
    public void findAllNewUsersTest() throws Exception {
        // given
        final String url = "/new-users";
        String email_1 = "new@new.com_1";
        String email_2 = "new@new.com_2";
        String name_1 = "새로운 유저 1";
        String name_2 = "새로운 유저 2";
        TempUser tempUser_1 = TestMakeObject.makeNewUserOb(email_1, name_1);
        TempUser tempUser_2 = TestMakeObject.makeNewUserOb(email_2, name_2);

        tempUserRepository.save(tempUser_1);
        tempUserRepository.save(tempUser_2);

        // when
        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token)); // token header에 담기

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(tempUser_1.getEmail()))
                .andExpect(jsonPath("$[0].name").value(tempUser_1.getName()));
    }

    @DisplayName("신청 유저들의 개별 정보를 볼수 있는 API 테스트")
    @Test
    public void findNewUsersTest() throws Exception {
        // given
        String email_1 = "new@new.com_1";
        String name_1 = "새로운 유저 1";
        TempUser tempUser_1 = TestMakeObject.makeNewUserOb(email_1, name_1);

        TempUser tempUserDB = tempUserRepository.save(tempUser_1);
        final String url = "/new-users/" + tempUserDB.getId();

        // when
        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token)); // token header에 담기

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tempUser.name").value(tempUserDB.getName()))
                .andExpect(jsonPath("$.tempUser.phoneNum").value(tempUserDB.getPhoneNum()))
                .andExpect(jsonPath("$.tempUser.major").value(tempUserDB.getMajor()))
                .andExpect(jsonPath("$.tempUser.gpa").value(tempUserDB.getGpa()))
                .andExpect(jsonPath("$.tempUser.address").value(tempUserDB.getAddress()))
                .andExpect(jsonPath("$.tempUser.specialtySkill").value(tempUserDB.getSpecialtySkill()))
                .andExpect(jsonPath("$.tempUser.mbti").value(tempUserDB.getMbti()))
                .andExpect(jsonPath("$.tempUser.studentId").value(tempUserDB.getStudentId()))
                .andExpect(jsonPath("$.tempUser.birthDate").value(tempUserDB.getBirthDate()))
                .andExpect(jsonPath("$.tempUser.advantages").value(tempUserDB.getAdvantages()))
                .andExpect(jsonPath("$.tempUser.disadvantage").value(tempUserDB.getDisadvantage()))
                .andExpect(jsonPath("$.tempUser.selfIntroduction").value(tempUserDB.getSelfIntroduction()))
                .andExpect(jsonPath("$.tempUser.photo").value(tempUserDB.getPhoto()))
                .andExpect(jsonPath("$.tempUser.yearOfRegistration").value(tempUserDB.getYearOfRegistration()))
                .andExpect(jsonPath("$.tempUser.email").value(tempUserDB.getEmail()))
                .andExpect(jsonPath("$.tempUser.password").value(tempUserDB.getPassword()))
                .andExpect(jsonPath("$.tempUser.regularMember").value(tempUserDB.isRegularMember()))
                .andExpect(jsonPath("$.tempUser.ob").value(tempUserDB.isOb()))
                .andExpect(jsonPath("$.nuriKing").value(true));
    }

    @DisplayName("신청 개별 유저의 승락을 하는 메서드 테스트")
    @Test
    public void acceptNewUserOfKingTest() throws Exception {
        // given
        String email_1 = "new@new.com_1";
        String name_1 = "새로운 유저_1";
        TempUser tempUser_1 = TestMakeObject.makeNewUserOb(email_1, name_1);

        TempUser tempUserDB = tempUserRepository.save(tempUser_1);
        final String url = "/new-users/" + tempUserDB.getId() + "/acceptance";

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        UserInfo newUser = userService.findByEmail(email_1);

        // then
        result
                .andExpect(status().isOk());
        assertThat(newUser.getEmail()).isEqualTo(tempUserDB.getEmail());

    }

    @DisplayName("신청 개별 유저의 거절 하는 메서드 테스트")
    @Test
    public void rejectNewUserOfKingTest() throws Exception {
        // given
        String email_1 = "new@new.com_1";
        String name_1 = "새로운 유저_1";
        TempUser tempUser_1 = TestMakeObject.makeNewUserOb(email_1, name_1);

        TempUser tempUserDB = tempUserRepository.save(tempUser_1);
        final String url = "/new-users/" + tempUserDB.getId() + "/reject";

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        // then
        result
                .andExpect(status().isOk());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tempUserService.findNewUsers(tempUserDB.getId());
        });

    }

    @DisplayName("나의 정보를 업데이트를 할때 기존에 기입하였던 정보를 find하는 테스트 함수")
    @Test
    public void testFindMypageToUpdate() throws Exception {
        // given
        final String url = "/mypage/update";


        UserInfo userInfo = userService.findUserById(1L);

        // when
        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token)); // token header에 담기

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userInfo.getName()))
                .andExpect(jsonPath("$.phoneNum").value(userInfo.getPhoneNum()))
                .andExpect(jsonPath("$.major").value(userInfo.getMajor()))
                .andExpect(jsonPath("$.gpa").value(userInfo.getGpa()))
                .andExpect(jsonPath("$.address").value(userInfo.getAddress()))
                .andExpect(jsonPath("$.specialtySkill").value(userInfo.getSpecialtySkill()))
                .andExpect(jsonPath("$.hobby").value(userInfo.getHobby()))
                .andExpect(jsonPath("$.mbti").value(userInfo.getMbti()))
                .andExpect(jsonPath("$.studentId").value(userInfo.getStudentId()))
                .andExpect(jsonPath("$.birthDate").value(userInfo.getBirthDate()))
                .andExpect(jsonPath("$.advantages").value(userInfo.getAdvantages()))
                .andExpect(jsonPath("$.disadvantage").value(userInfo.getDisadvantage()))
                .andExpect(jsonPath("$.selfIntroduction").value(userInfo.getSelfIntroduction()))
                .andExpect(jsonPath("$.photo").value(userInfo.getPhoto()))
                .andExpect(jsonPath("$.email").value(userInfo.getEmail()))
                .andExpect(jsonPath("$.yearOfRegistration").value(userInfo.getYearOfRegistration()))
                .andExpect(jsonPath("$.ob").value(userInfo.isOb()));
    }

    @DisplayName("출석을 하기위한 번호를 response하기 위한 method 테스트")
    @Test
    public void attendanceNumberControllerTest() throws Exception {
        // given
        final String url = "/attendance/find/number";
        NumOfTodayAttendence numOfTodayAttendence = numOfTodayAttendenceRepository.findById(1L)
                .orElseThrow();

        String attendenceNum = numOfTodayAttendence.getCheckNum();
        String dayAtNow = numOfTodayAttendence.getDay();

        // when
        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token)); // token header에 담기

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attendanceNum").value(attendenceNum))
                .andExpect(jsonPath("$.dayAtNow").value(dayAtNow));
    }


    @DisplayName("개개인의 유저에게 기능적인 정보를 find하는 컨트롤러")
    @Test
    public void userControlFindInfoTest() throws Exception {
        // given
        final String url = "/main/detail/1/control";
        AttendanceTime attendanceTime = attendanceTimeRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("not found: ")); // 찾아서 없으면 예외처리.;;

        // when
        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token)); // token header에 담기

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(attendanceTime.getName()))
                .andExpect(jsonPath("$.attendanceTime").value(attendanceTime.getMonday()));

    }

//    @DisplayName("개개인의 유저에게 기능적인 정보를 post하는 컨트롤러")
//    @Test
//    public void userControlPostInfoTest() throws Exception {
//        // given
//        final String url = "/main/detail/1/control";
//
//        String TestAttendanceTime = "15";
//        UserEachAttendanceControlRequest userControlRequest = new UserEachAttendanceControlRequest(TestAttendanceTime);
//
//        // 객체 suggestionsRequest을 Json으로 직렬화
//        final String requestBody = objectMapper.writeValueAsString(userControlRequest);
//
//        // when
//        ResultActions result = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .header("authorization", "Bearer " + token) // token header에 담기
//        );
//
//        AttendanceTime attendanceTime = attendanceTimeRepository.findById(1L)
//                .orElseThrow(() -> new IllegalArgumentException("not found: ")); // 찾아서 없으면 예외처리.;
//
//        // then
//        result
//                .andExpect(status().isOk());
//        assertThat(attendanceTime.getAttendanceTime()).isEqualTo(TestAttendanceTime);
//    }

    @DisplayName("개개의 유저의 장기 출장신청을 위한 api")
    @Test
    public void userExceptionAttendanceControlTest() throws Exception {
        // given
        final String url = "/main/detail/1/control/exception/attendance";
        boolean isExceptionAttendance = attendanceTimeRepository.findByUserId(1L).isExceptonAttendance();

        // when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );


        boolean result = attendanceTimeRepository.findByUserId(1L).isExceptonAttendance();
        // then
        if (isExceptionAttendance) {
            assertThat(result).isFalse();
        } else {
            assertThat(result).isTrue();
        }

    }

    @DisplayName("버튼을 누르면 장기 휴가 신청을 의미하는 Attendance_time 테이블의 exception의 값이 true/false가 반환된다.")
    @Test
    public void userFindExceptionAttendanceControlTest() throws Exception {
        // given
        final String url = "/main/detail/1/control/exception/attendance";
        boolean isExceptionAttendance = attendanceTimeRepository.findByUserId(1L).isExceptonAttendance();

        // when
        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token)); // token header에 담기

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exceptionAttendance").value(isExceptionAttendance));
    }

    @DisplayName("개인 별 휴가 신청을 위한 api")
    @Test
    public void applyVacationEachPersonTest() throws Exception {
        // given
        final String url = "/vacations/request/each";
        int vacationCount = userUtilRepository.findByUserId(1L).getCntVacation();

        // when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + token) // token header에 담기
        );

        int result = userUtilRepository.findByUserId(1L).getCntVacation();
        System.out.println("result = " + result);

        // then
        assertThat(result).isEqualTo(vacationCount - 1);
    }
}
