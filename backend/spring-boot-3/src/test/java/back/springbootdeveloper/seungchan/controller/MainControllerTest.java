package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.constant.filter.CustomHttpStatus;
import back.springbootdeveloper.seungchan.dto.request.UserEachAttendanceControlReqDto;
import back.springbootdeveloper.seungchan.dto.response.NoticeInformation;
import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import back.springbootdeveloper.seungchan.entity.Notice;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.entity.UserUtill;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.NoticeRepository;
import back.springbootdeveloper.seungchan.repository.UserRepository;
import back.springbootdeveloper.seungchan.service.*;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import back.springbootdeveloper.seungchan.testutills.TestUtills;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class MainControllerTest {

  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private DatabaseService databaseService;
  @Autowired
  private TestSetUp testSetUp;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserUtillService userUtillService;
  @Autowired
  private UserService userService;
  @Autowired
  private AttendanceService attendanceStateService;
  @Autowired
  private AttendanceTimeService attendanceTimeService;
  @Autowired
  private NoticeRepository noticeRepository;
  private String token;
  private Long kingUserId;
  private UserInfo kingUser;
  private UserUtill userUtillOfKingUser;
  private UserInfo nomalUser;
  private UserUtill userUtillOfNomalUser;
  private AttendanceStatus attendanceStatusOfKingUser;
  private AttendanceStatus attendanceStatusOfNomalUser;
  private UserInfo obUser;

  @BeforeEach
  void setUp() {
    databaseService.deleteAllDatabase();
    token = testSetUp.getToken(mockMvc);
    kingUserId = testSetUp.getKingUserId();
    kingUser = userService.findUserById(kingUserId);
    nomalUser = testSetUp.setUpUser();
    userUtillOfKingUser = userUtillService.findUserByUserId(kingUser.getId());
    userUtillOfNomalUser = userUtillService.findUserByUserId(nomalUser.getId());
    attendanceStatusOfKingUser = attendanceStateService.findById(kingUser.getId());
    attendanceStatusOfNomalUser = attendanceStateService.findById(nomalUser.getId());
    obUser = testSetUp.setUpOldUser();
  }


  @Test
  void 메인페이지_현재_회원들의_정보를_찾는다() throws Exception {
    // given
    final String url = "/main/ybs";

    // when
    ResultActions resultActions = mockMvc.perform(get(url)
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.ybUserInfomationList[0].name").value(kingUser.getName()))
        .andExpect(jsonPath("$.ybUserInfomationList[0].cntVacation").value(
            userUtillOfKingUser.getCntVacation()))
        .andExpect(jsonPath("$.ybUserInfomationList[0].weeklyData").value(
            TestUtills.StringToListFromAttendanceStateWeeklyDate(
                attendanceStatusOfKingUser.getWeeklyData())))
        .andExpect(jsonPath("$.ybUserInfomationList[1].name").value(nomalUser.getName()))
        .andExpect(jsonPath("$.ybUserInfomationList[1].cntVacation").value(
            userUtillOfNomalUser.getCntVacation()))
        .andExpect(jsonPath("$.ybUserInfomationList[1].weeklyData").value(
            TestUtills.StringToListFromAttendanceStateWeeklyDate(
                attendanceStatusOfNomalUser.getWeeklyData())))
        .andExpect(jsonPath("$.passAttendanceOfSearchUse").value(false));

  }

  @Test
  void 메인페이지_졸업_회원들의_정보를_찾는다() throws Exception {
    // given
    final String url = "/main/obs";

    // when
    ResultActions resultActions = mockMvc.perform(get(url)
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    resultActions
        .andExpect(jsonPath("$[0].obUserList[0].name").value(obUser.getName()))
        .andExpect(
            jsonPath("$[0].obUserList[0].yearOfRegistration").value(obUser.getYearOfRegistration()))
        .andExpect(jsonPath("$[0].obUserList[0].phoneNum").value(obUser.getPhoneNum()))
        .andExpect(jsonPath("$[0].obUserList[0].userId").value(obUser.getId()));
  }

  @Test
  public void 메인페이지_각_유저별_상세_조회_테스트_1() throws Exception {
    // given
    final String url = "/main/detail/{id}";

    // when
    final ResultActions resultActions = mockMvc.perform(get(url, kingUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    resultActions
        .andExpect(jsonPath("$.name").value(kingUser.getName()))
        .andExpect(jsonPath("$.major").value(kingUser.getMajor()))
        .andExpect(jsonPath("$.studentId").value(kingUser.getStudentId()))
        .andExpect(jsonPath("$.phoneNum").value(kingUser.getPhoneNum()))
        .andExpect(jsonPath("$.hobby").value(kingUser.getHobby()))
        .andExpect(jsonPath("$.specialtySkill").value(kingUser.getSpecialtySkill()))
        .andExpect(jsonPath("$.mbti").value(kingUser.getMbti()))
        .andExpect(jsonPath("$.userId").value(kingUser.getId()))
        .andExpect(jsonPath("$.ob").value(kingUser.isOb()))
        .andExpect(jsonPath("$.nuriKing").value(true));
  }

  @Test
  public void 메인페이지_각_유저별_상세_조회_테스트_2() throws Exception {
    // given
    final String url = "/main/detail/{id}";

    // when
    final ResultActions resultActions = mockMvc.perform(get(url, nomalUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    resultActions
        .andExpect(jsonPath("$.name").value(nomalUser.getName()))
        .andExpect(jsonPath("$.major").value(nomalUser.getMajor()))
        .andExpect(jsonPath("$.studentId").value(nomalUser.getStudentId()))
        .andExpect(jsonPath("$.phoneNum").value(nomalUser.getPhoneNum()))
        .andExpect(jsonPath("$.hobby").value(nomalUser.getHobby()))
        .andExpect(jsonPath("$.specialtySkill").value(nomalUser.getSpecialtySkill()))
        .andExpect(jsonPath("$.mbti").value(nomalUser.getMbti()))
        .andExpect(jsonPath("$.userId").value(nomalUser.getId()))
        .andExpect(jsonPath("$.ob").value(nomalUser.isOb()))
        .andExpect(jsonPath("$.nuriKing").value(true));
  }

  @Test
  void 회원_개인_각각_출석시간_찾기_테스트() throws Exception {
    final String url = "/main/detail/{id}/control";
    AttendanceTime attendanceTime = attendanceTimeService.findByUserId(kingUser.getId());

    // when
    final ResultActions resultActions = mockMvc.perform(get(url, kingUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );
    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(attendanceTime.getName()))
        .andExpect(jsonPath("$.mondayAttendanceTime").value(attendanceTime.getMonday()))
        .andExpect(jsonPath("$.tuesdayAttendanceTime").value(attendanceTime.getTuesday()))
        .andExpect(jsonPath("$.wednesdayAttendanceTime").value(attendanceTime.getWednesday()))
        .andExpect(jsonPath("$.thursdayAttendanceTime").value(attendanceTime.getThursday()))
        .andExpect(jsonPath("$.fridayAttendanceTime").value(attendanceTime.getFriday()));
  }

  @Test
  void 유저_개인_출석_시간_변경() throws Exception {
    final String url = "/main/detail/{id}/control";
    String UPDATE_NUMBER = "12";
    UserEachAttendanceControlReqDto requestDto = UserEachAttendanceControlReqDto.builder()
        .mondayAttendanceTime(UPDATE_NUMBER)
        .tuesdayAttendanceTime(UPDATE_NUMBER)
        .wednesdayAttendanceTime(UPDATE_NUMBER)
        .thursdayAttendanceTime(UPDATE_NUMBER)
        .fridayAttendanceTime(UPDATE_NUMBER)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    ResultActions result = mockMvc.perform(post(url, kingUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestBody)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    AttendanceTime result_attend = attendanceTimeService.findByUserId(kingUserId);

    // than
    result
        .andExpect(jsonPath("$.message").value("SUCCESS"))
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));

    assertThat(result_attend.getMonday()).isEqualTo(UPDATE_NUMBER);
    assertThat(result_attend.getTuesday()).isEqualTo(UPDATE_NUMBER);
    assertThat(result_attend.getWednesday()).isEqualTo(UPDATE_NUMBER);
    assertThat(result_attend.getThursday()).isEqualTo(UPDATE_NUMBER);
    assertThat(result_attend.getFriday()).isEqualTo(UPDATE_NUMBER);
  }

  @Test
  void 유저_개인_출석_시간_변경_빈칸_입력_1_예외_테스트() throws Exception {
    final String url = "/main/detail/{id}/control";
    String UPDATE_NUMBER = "12";
    UserEachAttendanceControlReqDto requestDto = UserEachAttendanceControlReqDto.builder()
        .mondayAttendanceTime("")
        .tuesdayAttendanceTime(UPDATE_NUMBER)
        .wednesdayAttendanceTime(UPDATE_NUMBER)
        .thursdayAttendanceTime(UPDATE_NUMBER)
        .fridayAttendanceTime(UPDATE_NUMBER)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(post(url, kingUserId)
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

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
  }

  @Test
  void 유저_개인_출석_시간_변경_빈칸_입력_2_예외_테스트() throws Exception {
    final String url = "/main/detail/{id}/control";
    String UPDATE_NUMBER = "12";
    UserEachAttendanceControlReqDto requestDto = UserEachAttendanceControlReqDto.builder()
        .mondayAttendanceTime(UPDATE_NUMBER)
        .tuesdayAttendanceTime("")
        .wednesdayAttendanceTime(UPDATE_NUMBER)
        .thursdayAttendanceTime(UPDATE_NUMBER)
        .fridayAttendanceTime(UPDATE_NUMBER)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(post(url, kingUserId)
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

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
  }

  @Test
  void 유저_개인_출석_시간_변경_빈칸_입력_3_예외_테스트() throws Exception {
    final String url = "/main/detail/{id}/control";
    String UPDATE_NUMBER = "12";
    UserEachAttendanceControlReqDto requestDto = UserEachAttendanceControlReqDto.builder()
        .mondayAttendanceTime(UPDATE_NUMBER)
        .tuesdayAttendanceTime(UPDATE_NUMBER)
        .wednesdayAttendanceTime("")
        .thursdayAttendanceTime(UPDATE_NUMBER)
        .fridayAttendanceTime(UPDATE_NUMBER)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(post(url, kingUserId)
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

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
  }

  @Test
  void 유저_개인_출석_시간_변경_빈칸_입력_4_예외_테스트() throws Exception {
    final String url = "/main/detail/{id}/control";
    String UPDATE_NUMBER = "12";
    UserEachAttendanceControlReqDto requestDto = UserEachAttendanceControlReqDto.builder()
        .mondayAttendanceTime(UPDATE_NUMBER)
        .tuesdayAttendanceTime(UPDATE_NUMBER)
        .wednesdayAttendanceTime(UPDATE_NUMBER)
        .thursdayAttendanceTime("")
        .fridayAttendanceTime(UPDATE_NUMBER)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(post(url, kingUserId)
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

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
  }

  @Test
  void 유저_개인_출석_시간_변경_빈칸_입력_5_예외_테스트() throws Exception {
    final String url = "/main/detail/{id}/control";
    String UPDATE_NUMBER = "12";
    UserEachAttendanceControlReqDto requestDto = UserEachAttendanceControlReqDto.builder()
        .mondayAttendanceTime(UPDATE_NUMBER)
        .tuesdayAttendanceTime(UPDATE_NUMBER)
        .wednesdayAttendanceTime(UPDATE_NUMBER)
        .thursdayAttendanceTime(UPDATE_NUMBER)
        .fridayAttendanceTime("")
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(post(url, kingUserId)
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

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
  }

  @ParameterizedTest
  @ValueSource(strings = {"00", "24", "abc", "123", "9", "25", "-1", "50", "99", "100", "!@#",
      "$%^"})
  void 유저_개인_출석_시간_변경_잘못된_출석시간_입력_1__예외_테스트(String input) throws Exception {
    final String url = "/main/detail/{id}/control";
    String UPDATE_NUMBER = "12";
    UserEachAttendanceControlReqDto requestDto = UserEachAttendanceControlReqDto.builder()
        .mondayAttendanceTime(input)
        .tuesdayAttendanceTime(UPDATE_NUMBER)
        .wednesdayAttendanceTime(UPDATE_NUMBER)
        .thursdayAttendanceTime(UPDATE_NUMBER)
        .fridayAttendanceTime(UPDATE_NUMBER)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(post(url, kingUserId)
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

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
  }

  @ParameterizedTest
  @ValueSource(strings = {"00", "24", "abc", "123", "9", "25", "-1", "50", "99", "100", "!@#",
      "$%^"})
  void 유저_개인_출석_시간_변경_잘못된_출석시간_입력_2__예외_테스트(String input) throws Exception {
    final String url = "/main/detail/{id}/control";
    String UPDATE_NUMBER = "12";
    UserEachAttendanceControlReqDto requestDto = UserEachAttendanceControlReqDto.builder()
        .mondayAttendanceTime(UPDATE_NUMBER)
        .tuesdayAttendanceTime(input)
        .wednesdayAttendanceTime(UPDATE_NUMBER)
        .thursdayAttendanceTime(UPDATE_NUMBER)
        .fridayAttendanceTime(UPDATE_NUMBER)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(post(url, kingUserId)
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

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
  }

  @ParameterizedTest
  @ValueSource(strings = {"00", "24", "abc", "123", "9", "25", "-1", "50", "99", "100", "!@#",
      "$%^"})
  void 유저_개인_출석_시간_변경_잘못된_출석시간_입력_3__예외_테스트(String input) throws Exception {
    final String url = "/main/detail/{id}/control";
    String UPDATE_NUMBER = "12";
    UserEachAttendanceControlReqDto requestDto = UserEachAttendanceControlReqDto.builder()
        .mondayAttendanceTime(UPDATE_NUMBER)
        .tuesdayAttendanceTime(UPDATE_NUMBER)
        .wednesdayAttendanceTime(input)
        .thursdayAttendanceTime(UPDATE_NUMBER)
        .fridayAttendanceTime(UPDATE_NUMBER)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(post(url, kingUserId)
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

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
  }

  @ParameterizedTest
  @ValueSource(strings = {"00", "24", "abc", "123", "9", "25", "-1", "50", "99", "100", "!@#",
      "$%^"})
  void 유저_개인_출석_시간_변경_잘못된_출석시간_입력_4__예외_테스트(String input) throws Exception {
    final String url = "/main/detail/{id}/control";
    String UPDATE_NUMBER = "12";
    UserEachAttendanceControlReqDto requestDto = UserEachAttendanceControlReqDto.builder()
        .mondayAttendanceTime(UPDATE_NUMBER)
        .tuesdayAttendanceTime(UPDATE_NUMBER)
        .wednesdayAttendanceTime(UPDATE_NUMBER)
        .thursdayAttendanceTime(input)
        .fridayAttendanceTime(UPDATE_NUMBER)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(post(url, kingUserId)
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

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
  }

  @ParameterizedTest
  @ValueSource(strings = {"00", "24", "abc", "123", "9", "25", "-1", "50", "99", "100", "!@#",
      "$%^"})
  void 유저_개인_출석_시간_변경_잘못된_출석시간_입력_5__예외_테스트(String input) throws Exception {
    final String url = "/main/detail/{id}/control";
    String UPDATE_NUMBER = "12";
    UserEachAttendanceControlReqDto requestDto = UserEachAttendanceControlReqDto.builder()
        .mondayAttendanceTime(UPDATE_NUMBER)
        .tuesdayAttendanceTime(UPDATE_NUMBER)
        .wednesdayAttendanceTime(UPDATE_NUMBER)
        .thursdayAttendanceTime(UPDATE_NUMBER)
        .fridayAttendanceTime(input)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(post(url, kingUserId)
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

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(stateCode).isEqualTo(CustomHttpStatus.DATA_VALID.value());
  }

  @Test
  public void 개인_장기_휴가_신청_테스트() throws Exception {
    // given
    final String url = "/main/detail/{id}/control/exception/attendance";
    Boolean isExceptionAttendance = attendanceTimeService.findByUserId(kingUserId)
        .isExceptonAttendance();

    // when
    ResultActions result = mockMvc.perform(post(url, kingUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    boolean result_exception = attendanceTimeService.findByUserId(kingUserId)
        .isExceptonAttendance();
    // then
    if (isExceptionAttendance) {
      assertThat(result_exception).isFalse();
    } else {
      assertThat(result_exception).isTrue();
    }

    result
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
    // post 요청을 2번하여 toggle 테스트
    Boolean isExceptionAttendance_2 = attendanceTimeService.findByUserId(kingUserId)
        .isExceptonAttendance();

    // when
    ResultActions result_2 = mockMvc.perform(post(url, kingUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    boolean result_exception_2 = attendanceTimeService.findByUserId(kingUserId)
        .isExceptonAttendance();
    // then
    if (isExceptionAttendance_2) {
      assertThat(result_exception_2).isFalse();
    } else {
      assertThat(result_exception_2).isTrue();
    }

    result_2
        .andExpect(jsonPath("$.httpStatus").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()));
  }

  @Test
  public void 개인_장기_휴가_신청_확인_테스트_1() throws Exception {
    // given
    final String url = "/main/detail/{id}/control/exception/attendance";
    Boolean isExceptionAttendance = attendanceTimeService.findByUserId(kingUserId)
        .isExceptonAttendance();

    // when
    ResultActions resultActions = mockMvc.perform(get(url, kingUserId)
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    resultActions
        .andExpect(jsonPath("$.exceptionAttendance").value(isExceptionAttendance));
  }

  @Test
  public void 개인_장기_휴가_신청_확인_테스트_2() throws Exception {
    // given
    final String url = "/main/detail/{id}/control/exception/attendance";
    Boolean isExceptionAttendance = attendanceTimeService.findByUserId(nomalUser.getId())
        .isExceptonAttendance();

    // when
    ResultActions resultActions = mockMvc.perform(get(url, nomalUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    resultActions
        .andExpect(jsonPath("$.exceptionAttendance").value(isExceptionAttendance));
  }

  @Test
  void 회원_추방_기능() throws Exception {
    // given
    final String url = "/main/detail/{id}/control/delete";
    Boolean isDeleteUser = false;

    // when
    ResultActions resultActions = mockMvc.perform(post(url, nomalUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    try {
      userRepository.findById(nomalUser.getId()).orElseThrow(EntityNotFoundException::new);
    } catch (Exception e) {
      isDeleteUser = true;
    }

    // then
    resultActions
        .andExpect(jsonPath("$.message").value(ResponseMessage.SUCCESS_DELETE_USER.get()));
    assertThat(isDeleteUser).isTrue();
  }

  @Test
  void 회원_추방_예외_실장_테스트() throws Exception {
    // given
    final String url = "/main/detail/{id}/control/delete";

    // when
    ResultActions resultActions = mockMvc.perform(post(url, kingUserId)
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    resultActions
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_IS_NURI_KING.get()));
  }

  @Test
  void 회원_추방_예외_졸업_테스트() throws Exception {
    // given
    final String url = "/main/detail/{id}/control/delete";
    final Long graduationId = userRepository.findAllByIsOb(true).get(0).getId();

    // when
    ResultActions resultActions = mockMvc.perform(post(url, graduationId)
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // then
    resultActions
        .andExpect(jsonPath("$.message").value(ResponseMessage.BAD_IS_GRADUATION_USER.get()));
  }

  @Test
  void 일반_회원_졸업_토글_기능_테스트() throws Exception {
    // given
    final String url = "/main/detail/{id}/control/toggle/grade";
    final UserInfo targetUser = nomalUser;

    // when
    ResultActions resultActions = mockMvc.perform(post(url, targetUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    UserInfo resultUserInfo = userRepository.findById(targetUser.getId()).get();
    Boolean resultIsOd = resultUserInfo.isOb();

    resultActions
        .andExpect(jsonPath("$.message").value(
            RESPONSE_MESSAGE.SUCCESE_TOGLE_GRADUATION(targetUser.getName())));
    assertThat(resultIsOd).isEqualTo(!targetUser.isOb());
  }

  @Test
  void 일반_졸업_회원_토글_기능_테스트() throws Exception {
    // given
    final String url = "/main/detail/{id}/control/toggle/grade";
    final UserInfo targetUser = nomalUser;
    userService.toggleGrade(targetUser.getId(), true);

    // when
    ResultActions resultActions = mockMvc.perform(post(url, targetUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    UserInfo resultUserInfo = userRepository.findById(targetUser.getId()).get();
    Boolean resultIsOd = resultUserInfo.isOb();

    resultActions
        .andExpect(jsonPath("$.message").value(
            RESPONSE_MESSAGE.SUCCESE_TOGLE_MEMBER(targetUser.getName())));
    assertThat(resultIsOd).isFalse();
  }

  @Test
  void 일반_졸업_회원_토글_기능_예외_실장_적용_테스트() throws Exception {
    // given
    final String url = "/main/detail/{id}/control/toggle/grade";
    final UserInfo targetUser = kingUser;
    userService.toggleGrade(targetUser.getId(), true);

    // when
    ResultActions resultActions = mockMvc.perform(post(url, targetUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    resultActions
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_IS_NURI_KING.get()));
  }

  @Test
  void 실장_권한_주는_기능_테스트() throws Exception {
    // given
    final String url = "/main/detail/{id}/control/give/king";
    final UserInfo targetUser = nomalUser;
    final UserInfo myUser = kingUser;

    // when
    ResultActions resultActions = mockMvc.perform(post(url, targetUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    UserUtill resultKingUserUtill = userUtillService.findUserByUserId(myUser.getId());

    resultActions
        .andExpect(jsonPath("$.message").value(
            RESPONSE_MESSAGE.SUCCESE_GIVE_KING(targetUser.getName(), myUser.getName())));

    assertThat(resultKingUserUtill.isNuriKing()).isFalse();
  }

  @Test
  void 실장_권한_주는_기능_예외_타겟_졸업_유저_테스트() throws Exception {
    // given
    final String url = "/main/detail/{id}/control/give/king";
    final UserInfo targetUser = obUser;
    final UserInfo myUser = nomalUser;

    // when
    ResultActions resultActions = mockMvc.perform(post(url, targetUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    resultActions
        .andExpect(jsonPath("$.message").value(
            ResponseMessage.BAD_NOT_GIVE_KING_GRADUATION_USER.get()));
  }

  @Test
  void 공지_사항_확인_테스트() throws Exception {
    final String url = "/main/notices";

    // 검증을 위한 테스트
    List<Notice> notices = noticeRepository.findAll();
    List<NoticeInformation> noticeInformations = notices.stream().map(NoticeInformation::new)
        .toList();
    // when

    ResultActions result = mockMvc.perform(get(url, kingUser.getId())
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("authorization", "Bearer " + token) // token header에 담기
    );

    // than
    for (int i = 0; i < noticeInformations.size(); i++) {
      result
          .andExpect(jsonPath("$.result.noticeInformations[" + i + "].noticeId").value(
              noticeInformations.get(i).getNoticeId()))
          .andExpect(jsonPath("$.result.noticeInformations[" + i + "].title").value(
              noticeInformations.get(i).getTitle()))
          .andExpect(jsonPath("$.result.noticeInformations[" + i + "].content").value(
              noticeInformations.get(i).getContent()))
          .andExpect(jsonPath("$.result.noticeInformations[" + i + "].date").value(
              noticeInformations.get(i).getDate()));
    }
  }
}

