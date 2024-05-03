package back.springbootdeveloper.seungchan.domain;

import back.springbootdeveloper.seungchan.annotation.NctSpringBootTest;
import back.springbootdeveloper.seungchan.constant.regexp.RegexpConstant;
import back.springbootdeveloper.seungchan.entity.Suggestion;
import back.springbootdeveloper.seungchan.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@NctSpringBootTest
class TemporaryPasswordTest {

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

  @BeforeEach
  void setUp() {
  }

  @Test
  void 커스텀() {
    System.out.println("attendanceStatusRepository.count() = " + attendanceStatusRepository.count());
    System.out.println("attendanceTimeRepository.count() = " + attendanceTimeRepository.count());
    System.out.println("noticeRepository.count() = " + noticeRepository.count());
    System.out.println("numOfTodayAttendenceRepository.count() = " + numOfTodayAttendenceRepository.count());
    System.out.println("suggestionRepository.count() = " + suggestionRepository.count());
  }


  @Test
  void 비밀번호_형식에_맞는_임시_비밀번호_생성_확인_테스트() {
    // given
    TemporaryPassword temporaryPassword = new TemporaryPassword();
    String regexPassword = RegexpConstant.PASSWORD.get();

    // when
    // then
    for (int i = 0; i < 10000; i++) {
      Boolean result = true;

      String tempPasswordString = temporaryPassword.generate();
      if (!tempPasswordString.matches(regexPassword)) {
        result = false;
      }

      assertThat(result).isTrue();
    }
  }
}