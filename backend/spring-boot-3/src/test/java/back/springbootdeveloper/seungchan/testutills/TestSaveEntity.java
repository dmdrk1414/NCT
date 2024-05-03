package back.springbootdeveloper.seungchan.testutills;

import back.springbootdeveloper.seungchan.constant.SuggestionConstant;
import back.springbootdeveloper.seungchan.controller.config.BCryptPasswordEncoderObject;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.repository.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestSaveEntity {

  @Autowired
  private AttendanceStatusRepository attendanceStatusRepository;
  @Autowired
  private AttendanceTimeRepository attendanceTimeRepository;
  @Autowired
  private NoticeRepository noticeRepository;
  @Autowired
  private NumOfTodayAttendenceRepository numOfTodayAttendenceRepository;
  @Autowired
  private SuggestionRepository suggestionRepository;
  @Autowired
  private UserRepository userRepository;

  public void creatEntityTest() throws Exception {
    // user 100명 기준 - 1번 실장 - 2번 총무
    final int userNum = 100;
    // AttendanceStatus Entity
    createAttendanceStatusEntity(userNum);
    // AttendanceTime
    createAttendanceTime(100);
    // Notice Entity
    createNoticeEntity();
    // NumOfTodayAttendance
    createNumOfTodayAttendance();
    // Suggestion
    createSuggestion();
    // UserInfo
    createUserInfo(userNum);
  }

  /**
   * UserInfo
   */
  private void createUserInfo(final int userNum) {
    List<UserInfo> userInfos = new ArrayList<>();
    for (int i = 1; i <= userNum; i++) {
      String TEST_PHONE_NUM = "";
      String TEST_STUDENT_ID = "";
      String TEST_BIRTH_DATE = "";
      if (i <= 9) {
        TEST_PHONE_NUM = "010-0000-000" + i;
        TEST_STUDENT_ID = "1111222" + i;
        TEST_BIRTH_DATE = "00000" + i;
      }
      if (10 <= i && i <= 99) {
        TEST_PHONE_NUM = "010-0000-00" + i;
        TEST_STUDENT_ID = "111122" + i;
        TEST_BIRTH_DATE = "0000" + i;
      }
      if (100 <= i && i <= 999) {
        TEST_PHONE_NUM = "010-0000-0" + i;
        TEST_STUDENT_ID = "11112" + i;
        TEST_BIRTH_DATE = "000" + i;
      }
      if (1000 <= i && i <= 9999) {
        TEST_PHONE_NUM = "010-0000-" + i;
        TEST_STUDENT_ID = "1111" + i;
        TEST_BIRTH_DATE = "00" + i;
      }
      UserInfo user = UserInfo.builder()
          .name("유저_" + i)
          .phoneNum(TEST_PHONE_NUM)
          .major("테스트 학과_" + i)
          .gpa("4.0")
          .address("테스트 주소")
          .specialtySkill("테스트 특기")
          .hobby("테스트 취미")
          .mbti("SEXY")
          .studentId(TEST_STUDENT_ID)
          .birthDate(TEST_BIRTH_DATE)
          .advantages("테스트 장점")
          .disadvantage("테스트 단점")
          .selfIntroduction("테스트 자기소개")
          .photo("")
          .isOb(false)
          .build();
      user.setEmail("test_" + i + "@test.com");
      user.setPassword(BCryptPasswordEncoderObject.getCryptPassword("1234"));
      user.setRegularMember(true);

      userInfos.add(user);
    }

    userRepository.saveAll(userInfos);
  }

  /**
   * Suggestion
   */
  private void createSuggestion() {
    List<Suggestion> suggestions = new ArrayList<>();

    for (int i = 1; i <= 20; i++) {
      suggestions.add(
          Suggestion.builder()
              .classification(SuggestionConstant.SUGGESTION.getClassification())
              .title("테스트 건의 사항_" + i)
              .holidayPeriod("")
              .build()
      );
    }
    for (int i = 21; i <= 40; i++) {
      suggestions.add(
          Suggestion.builder()
              .classification(SuggestionConstant.FREEDOM.getClassification())
              .title("테스트 자유 제목_" + i)
              .holidayPeriod("")
              .build()
      );
    }

    suggestionRepository.saveAll(suggestions);
  }

  /**
   * NumOfTodayAttendance
   */
  private void createNumOfTodayAttendance() {
    // 현재 날짜 가져오기
    LocalDate today = LocalDate.now();
    // 날짜 포맷 지정
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // 날짜를 문자열로 변환
    String formattedDate = today.format(formatter);
    numOfTodayAttendenceRepository.save(
        NumOfTodayAttendence.builder()
            .checkNum("1234")
            .day(formattedDate)
            .build()
    );
  }

  /**
   * Attendance Time
   *
   * @param userNum
   */
  private void createAttendanceTime(final int userNum) {
    List<AttendanceTime> attendanceTimes = new ArrayList<>();

    for (int i = 1; i <= 100; i++) {
      attendanceTimes.add(
          AttendanceTime.builder()
              .userId(Long.valueOf(i))
              .name("유저_" + i)
              .monday("09")
              .tuesday("09")
              .wednesday("09")
              .thursday("09")
              .friday("09")
              .isExceptonAttendance(false)
              .build()
      );
    }

    attendanceTimeRepository.saveAll(attendanceTimes);
  }

  /**
   * AttendanceStatus
   *
   * @param userNum
   */
  private void createAttendanceStatusEntity(final int userNum) {
    List<AttendanceStatus> attendanceStatuses = new ArrayList<>();

    for (int i = 1; i <= userNum; i++) {
      attendanceStatuses.add(
          AttendanceStatus.builder()
              .userId(Long.valueOf(userNum))
              .name("유저_" + i)
              .vacationDates("")
              .absenceDates("")
              .weeklyData("[0,0,0,0,0]")
              .build()
      );
    }

    attendanceStatusRepository.saveAll(attendanceStatuses);
  }

  /**
   * Notice Entity
   */
  private void createNoticeEntity() {
    List<Notice> notices = new ArrayList<>();
    for (int i = 1; i <= 100; i++) {
      notices.add(
          Notice.builder()
              .title("테스트 공지사항 제목_" + i)
              .content("테스트 공지사항 내용_" + i)
              .build()
      );
    }

    noticeRepository.saveAll(notices);
  }
}
