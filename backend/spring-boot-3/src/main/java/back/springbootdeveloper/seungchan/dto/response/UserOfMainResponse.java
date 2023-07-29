package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.domain.AttendanceStatus;
import back.springbootdeveloper.seungchan.domain.UserUtill;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserOfMainResponse {
    private String name;
    private int cntVacation;
    private String weeklyData;

    @Builder
    public UserOfMainResponse(AttendanceStatus attendanceStatus, UserUtill userUtill) {
        this.name = userUtill.getName();
        this.cntVacation = userUtill.getCntVacation();
        this.weeklyData = attendanceStatus.getWeeklyData();
    }
}
