package back.springbootdeveloper.seungchan.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEachAttendanceControlReqDto {
    private String mondayAttendanceTime;
    private String tuesdayAttendanceTime;
    private String wednesdayAttendanceTime;
    private String thursdayAttendanceTime;
    private String fridayAttendanceTime;

    public List<String> getAttendanceTimes() {
        return List.of(mondayAttendanceTime, tuesdayAttendanceTime,
                wednesdayAttendanceTime, thursdayAttendanceTime, fridayAttendanceTime);
    }
}
