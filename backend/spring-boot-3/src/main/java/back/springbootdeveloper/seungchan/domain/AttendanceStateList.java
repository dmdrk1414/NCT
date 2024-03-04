package back.springbootdeveloper.seungchan.domain;

import back.springbootdeveloper.seungchan.constant.entity.ATTENDANCE_STATE;
import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
public class AttendanceStateList {

  private List<ATTENDANCE_STATE> attendanceStates;
  private Long userId;

  @Builder
  public AttendanceStateList(final List<ATTENDANCE_STATE> attendanceStates, final Long userId) {
    this.attendanceStates = attendanceStates;
    this.userId = userId;
  }
}
