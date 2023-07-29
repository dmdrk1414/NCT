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
public class YbUserOfMainResponse {
    private String name;
    private int cntVacation;
    private String weeklyData;

    @Builder
    public YbUserOfMainResponse(AttendanceStatus attendanceStatus, UserUtill userUtill) {
        this.name = userUtill.getName();
        this.cntVacation = userUtill.getCntVacation();
        this.weeklyData = attendanceStatus.getWeeklyData();
    }
}
