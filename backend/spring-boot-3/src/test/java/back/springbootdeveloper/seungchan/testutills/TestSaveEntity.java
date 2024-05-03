package back.springbootdeveloper.seungchan.testutills;

import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import back.springbootdeveloper.seungchan.entity.Notice;
import back.springbootdeveloper.seungchan.repository.AttendanceStatusRepository;
import back.springbootdeveloper.seungchan.repository.NoticeRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestSaveEntity {

  @Autowired
  private AttendanceStatusRepository attendanceStatusRepository;
  @Autowired
  private NoticeRepository noticeRepository;

  public void creatEntityTest() throws Exception {
    // user 100명 기준 - 1번 실장 - 2번 총무
    final int userNum = 100;
    // AttendanceStatus Entity
    createAttendanceStatusEntity(userNum);
    // Notice Entity
    createNoticeEntity();
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
