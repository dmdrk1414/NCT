package back.springbootdeveloper.seungchan.testutills;

import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import back.springbootdeveloper.seungchan.entity.Notice;
import back.springbootdeveloper.seungchan.entity.NumOfTodayAttendence;
import back.springbootdeveloper.seungchan.repository.AttendanceStatusRepository;
import back.springbootdeveloper.seungchan.repository.AttendanceTimeRepository;
import back.springbootdeveloper.seungchan.repository.NoticeRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import back.springbootdeveloper.seungchan.repository.NumOfTodayAttendenceRepository;
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
