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
public class UserControlResponse {
    private String attendanceTime;
    private String name;

    public UserControlResponse(AttendanceTime attendanceTime) {
        this.attendanceTime = attendanceTime.getAttendanceTime();
        this.name = attendanceTime.getName();
    }
}
