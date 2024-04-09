package back.springbootdeveloper.seungchan.constant.entity;

import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import lombok.Getter;

@Getter
public enum ATTENDANCE_STATE {
  UNDECIDED("UNDECIDED", 0),
  ATTENDANCE("ATTENDANCE", 1),
  ABSENCE("ABSENCE", -1),
  VACATION("VACATION", 2);

  private String state;
  private Integer num;
 
  ATTENDANCE_STATE(final String state, final Integer num) {
    this.state = state;
    this.num = num;
  }

  public boolean is(ATTENDANCE_STATE attendanceState) {
    return this.state.equals(attendanceState.getState());
  }

  public boolean is(Integer number) {

    return this.num.equals(number);
  }
}
