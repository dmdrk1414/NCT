package back.springbootdeveloper.seungchan.service;

import static org.junit.jupiter.api.Assertions.*;

import back.springbootdeveloper.seungchan.domain.AttendanceStateList;
import back.springbootdeveloper.seungchan.entity.AttendanceWeekDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AttendanceServiceTest {

  @Autowired
  private AttendanceService attendanceService;

  @Test
  void getAllAttendanceWeekState() {
    List<AttendanceStateList> attendanceStateLists = this.attendanceService.getAllAttendanceWeekState();

    for (final AttendanceStateList attendanceStateList : attendanceStateLists) {
      AttendanceWeekDate attendanceWeekDate = new AttendanceWeekDate(attendanceStateList);

      System.out.println("attendanceWeekDate.getAllAttendanceWeekDates() = "
          + attendanceWeekDate.getAllAttendanceWeekDates());
    }
  }
}