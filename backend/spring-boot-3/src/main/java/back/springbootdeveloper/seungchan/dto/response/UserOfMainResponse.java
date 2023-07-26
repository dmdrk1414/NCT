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
    private String attendances;
    private int cntVacation;

    @Builder
    public UserOfMainResponse(AttendanceStatus attendanceStatus, UserUtill userUtill) {
        this.name = userUtill.getName();
        this.attendances = attendanceStatus.getAbsenceDates();
        this.cntVacation = userUtill.getCntVacation();
    }
}
