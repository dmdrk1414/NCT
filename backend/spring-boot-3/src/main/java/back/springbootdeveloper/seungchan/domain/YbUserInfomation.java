package back.springbootdeveloper.seungchan.domain;

import back.springbootdeveloper.seungchan.entity.AttendanceStatus;
import back.springbootdeveloper.seungchan.entity.UserUtill;
import back.springbootdeveloper.seungchan.util.Utill;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class YbUserInfomation {
    private String name;
    private int cntVacation;
    private List<Integer> weeklyData;
    private Long userId;

    @Builder
    public YbUserInfomation(AttendanceStatus attendanceStatus, UserUtill userUtill) {
        this.name = userUtill.getName();
        this.cntVacation = userUtill.getCntVacation();
        this.weeklyData = Utill.extractNumbers(attendanceStatus.getWeeklyData());
        this.userId = userUtill.getUserId();
    }
}
