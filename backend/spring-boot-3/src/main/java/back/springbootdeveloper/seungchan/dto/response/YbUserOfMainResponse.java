package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.domain.AttendanceStatus;
import back.springbootdeveloper.seungchan.domain.UserUtill;
import back.springbootdeveloper.seungchan.util.Utill;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class YbUserOfMainResponse {
    private String name;
    private int cntVacation;
    private List<Integer> weeklyData;
    private Long userId;

    @Builder
    public YbUserOfMainResponse(AttendanceStatus attendanceStatus, UserUtill userUtill) {
        this.name = userUtill.getName();
        this.cntVacation = userUtill.getCntVacation();
        this.weeklyData = Utill.extractNumbers(attendanceStatus.getWeeklyData());
        this.userId = userUtill.getUserId();
    }
}
