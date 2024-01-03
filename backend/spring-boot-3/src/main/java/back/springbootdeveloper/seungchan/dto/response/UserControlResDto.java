package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserControlResDto {
    private String mondayAttendanceTime;
    private String tuesdayAttendanceTime;
    private String wednesdayAttendanceTime;
    private String thursdayAttendanceTime;
    private String fridayAttendanceTime;
    private String name;

    public UserControlResDto(AttendanceTime attendanceTime) {
        this.mondayAttendanceTime = attendanceTime.getMonday();
        this.tuesdayAttendanceTime = attendanceTime.getTuesday();
        this.wednesdayAttendanceTime = attendanceTime.getWednesday();
        this.thursdayAttendanceTime = attendanceTime.getThursday();
        this.fridayAttendanceTime = attendanceTime.getFriday();
        this.name = attendanceTime.getName();
    }
}
