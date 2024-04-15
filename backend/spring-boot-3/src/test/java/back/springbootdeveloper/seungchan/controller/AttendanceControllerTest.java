package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.filter.CustomHttpStatus;
import back.springbootdeveloper.seungchan.constant.filter.exception.ExceptionMessage;
import back.springbootdeveloper.seungchan.dto.request.AttendanceNumberReqDto;
import back.springbootdeveloper.seungchan.entity.NumOfTodayAttendence;
import back.springbootdeveloper.seungchan.service.*;
import back.springbootdeveloper.seungchan.testutills.TestSetUp;
import back.springbootdeveloper.seungchan.testutills.TestUtills;
import back.springbootdeveloper.seungchan.util.DayUtill;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class AttendanceControllerTest {

  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private DatabaseService databaseService;
  @Autowired
  private TestSetUp testSetUp;
  private String token;
  @Autowired
  private NumOfTodayAttendenceService numOfTodayAttendenceService;
  @Autowired
  private AttendanceService attendanceService;

  @BeforeEach
  void setUp() {
    databaseService.deleteAllDatabase();
    token = testSetUp.getToken(mockMvc);
  }

  @Test
  void 개인별_출석번호_입력_요청_확인_테스트() throws Exception {
    if (!DayUtill.isWeekendDay()) {
      // given
      final String url = "/attendance/number";
      Integer attendanceNumber = 1234;
      String day = getTodayString();
      numOfTodayAttendenceService.save(day, attendanceNumber);

      AttendanceNumberReqDto attendanceNumberReqDto = AttendanceNumberReqDto.builder()
          .numOfAttendance(String.valueOf(attendanceNumber))
          .build();

      // when
      final String requestBody = objectMapper.writeValueAsString(attendanceNumberReqDto);

      ResultActions result = mockMvc.perform(post(url)
          .accept(MediaType.APPLICATION_JSON)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .content(requestBody)
          .header("authorization", "Bearer " + token) // token header에 담기
      );

      // than
      result
          .andExpect(jsonPath("$.passAtNow").value(true));
    }
  }

  @Test
  void 개인별_출석번호_입력_요청_확인_주말_출석_예외_테스트() throws Exception {
    if (DayUtill.isWeekendDay()) {
      // given
      final String url = "/attendance/number";
      Integer attendanceNumber = 1234;
      String day = getTodayString();
      numOfTodayAttendenceService.save(day, attendanceNumber);

      AttendanceNumberReqDto attendanceNumberReqDto = AttendanceNumberReqDto.builder()
          .numOfAttendance(String.valueOf(attendanceNumber))
          .build();

      // when
      final String requestBody = objectMapper.writeValueAsString(attendanceNumberReqDto);

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

      assertThat(message).isEqualTo(ExceptionMessage.WEEKEND_MESSAGE.get());
      assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
      assertThat(stateCode).isEqualTo(CustomHttpStatus.WEEKEND.value());
    }
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"", " "})
  void 개인별_출석번호_입력_요청_확인_예외_테스트_출석번호_not_blank(String inputValue) throws Exception {
    // given
    final String url = "/attendance/number";
    String day = getTodayString();
    Integer attendanceNumber = 1234;
    numOfTodayAttendenceService.save(day, Integer.parseInt(String.valueOf(attendanceNumber)));

    AttendanceNumberReqDto attendanceNumberReqDto = AttendanceNumberReqDto.builder()
        .numOfAttendance(inputValue)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(attendanceNumberReqDto);

    MvcResult result = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andReturn();

    MockHttpServletResponse response = result.getResponse();

    // JSON 응답을 Map으로 변환
    HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {
      "1",
      "12",
      "123",
  })
  void 개인별_출석번호_입력_요청_확인_예외_테스트_출석번호_최소길이_4(String inputValue) throws Exception {
    // given
    final String url = "/attendance/number";
    String day = getTodayString();
    Integer attendanceNumber = 1234;
    numOfTodayAttendenceService.save(day, Integer.parseInt(String.valueOf(attendanceNumber)));

    AttendanceNumberReqDto attendanceNumberReqDto = AttendanceNumberReqDto.builder()
        .numOfAttendance(inputValue)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(attendanceNumberReqDto);

    MvcResult result = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andReturn();

    MockHttpServletResponse response = result.getResponse();

    // JSON 응답을 Map으로 변환
    HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {
      "abcd",
      "12ab",
      "5678a",
      "1234a",
      "1234!",
      "1234! ",
      " 1234!",
      "567 8",
  })
    // 틀린 출석 번호 예시

  void 개인별_출석번호_입력_요청_확인_예외_테스트_숫자_입력_검증(String inputValue) throws Exception {
    // given
    final String url = "/attendance/number";
    String day = getTodayString();
    Integer attendanceNumber = 1234;
    numOfTodayAttendenceService.save(day, Integer.parseInt(String.valueOf(attendanceNumber)));

    AttendanceNumberReqDto attendanceNumberReqDto = AttendanceNumberReqDto.builder()
        .numOfAttendance(inputValue)
        .build();

    // when
    final String requestBody = objectMapper.writeValueAsString(attendanceNumberReqDto);

    MvcResult result = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andReturn();

    MockHttpServletResponse response = result.getResponse();

    // JSON 응답을 Map으로 변환
    HttpStatus httpStatus = TestUtills.getHttpStatusFromResponse(response);

    assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void 개인별_출석번호_입력_요청_확인_예외_테스트_이미_휴가를_사용했다면() throws Exception {
    if (!DayUtill.isWeekendDay()) {
      // given
      final String url = "/attendance/number";
      Integer attendanceNumber = 1234;
      String day = getTodayString();
      numOfTodayAttendenceService.save(day, Integer.parseInt(String.valueOf(attendanceNumber)));
      Long userId = testSetUp.getKingUserId();
      attendanceService.updateVacationDate2PassVacation(userId);

      AttendanceNumberReqDto attendanceNumberReqDto = AttendanceNumberReqDto.builder()
          .numOfAttendance(String.valueOf(attendanceNumber))
          .build();

      // when
      final String requestBody = objectMapper.writeValueAsString(attendanceNumberReqDto);

      ResultActions result = mockMvc.perform(post(url)
          .accept(MediaType.APPLICATION_JSON)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .content(requestBody)
          .header("authorization", "Bearer " + token) // token header에 담기
      );

      // than
      result
          .andExpect(jsonPath("$.passAtNow").value(false));
    }
  }


  @Test
  void 개인별_출석번호_입력_요청_확인_예외_테스트_결석을_하였다면_휴가를_사용할_수없다() throws Exception {
    if (!DayUtill.isWeekendDay()) {
      // given
      final String url = "/attendance/number";
      Integer attendanceNumber = 1234;
      String day = getTodayString();
      numOfTodayAttendenceService.save(day, Integer.parseInt(String.valueOf(attendanceNumber)));
      Long userId = testSetUp.getKingUserId();
      attendanceService.updateAbsenceVacationDate(userId);

      AttendanceNumberReqDto attendanceNumberReqDto = AttendanceNumberReqDto.builder()
          .numOfAttendance(String.valueOf(attendanceNumber))
          .build();

      // when
      final String requestBody = objectMapper.writeValueAsString(attendanceNumberReqDto);

      ResultActions result = mockMvc.perform(post(url)
          .accept(MediaType.APPLICATION_JSON)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .content(requestBody)
          .header("authorization", "Bearer " + token) // token header에 담기
      );

      // than
      result
          .andExpect(jsonPath("$.passAtNow").value(false));
    }
  }

  @Test
  void 개인별_출석번호_입력_요청_확인_예외_테스트_이미_출석을_하였다면_휴가를_사용할_수_없다() throws Exception {
    if (!DayUtill.isWeekendDay()) {
      // given
      final String url = "/attendance/number";
      Integer attendanceNumber = 1234;
      String day = getTodayString();
      numOfTodayAttendenceService.save(day, Integer.parseInt(String.valueOf(attendanceNumber)));
      Long userId = testSetUp.getKingUserId();
      attendanceService.updateAttendanceToday(userId);

      AttendanceNumberReqDto attendanceNumberReqDto = AttendanceNumberReqDto.builder()
          .numOfAttendance(String.valueOf(attendanceNumber))
          .build();

      // when
      final String requestBody = objectMapper.writeValueAsString(attendanceNumberReqDto);

      ResultActions result = mockMvc.perform(post(url)
          .accept(MediaType.APPLICATION_JSON)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .content(requestBody)
          .header("authorization", "Bearer " + token) // token header에 담기
      );

      // than
      result
          .andExpect(jsonPath("$.passAtNow").value(false));
    }
  }

  private String getTodayString() {
    // 현재 날짜를 가져오기
    LocalDate currentDate = LocalDate.now();

    // DateTimeFormatter를 사용하여 원하는 포맷으로 날짜를 변환
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return currentDate.format(formatter);
  }

  @Test
  void 출석_번호_조회_테스트() throws Exception {
    if (DayUtill.isWeekDay()) {
      final String url = "/attendance/find/number";
      NumOfTodayAttendence numOfTodayAttendence = testSetUp.getNumOfTodayAttendence();
      String attendanceNum = numOfTodayAttendence.getCheckNum();
      String dayAtNow = numOfTodayAttendence.getDay();

      // when
      ResultActions result = mockMvc.perform(get(url)
          .accept(MediaType.APPLICATION_JSON)
          .header("authorization", "Bearer " + token) // token header에 담기
      );

      // then
      result
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.attendanceNum").value(attendanceNum))
          .andExpect(jsonPath("$.dayAtNow").value(dayAtNow));
    }
  }
}