package back.springbootdeveloper.seungchan.constant;

import lombok.Getter;

@Getter
public enum AttendanceStateConstant {
    VACATION(2),
    ATTENDANCE(1),
    ABSENCE(-1),
    UN_DECIDED(0);

    private Integer state;

    AttendanceStateConstant(Integer state) {
        this.state = state;
    }
}
