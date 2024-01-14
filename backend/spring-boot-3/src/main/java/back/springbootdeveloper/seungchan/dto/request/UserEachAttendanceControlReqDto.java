package back.springbootdeveloper.seungchan.dto.request;

import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEachAttendanceControlReqDto {
    private String mondayAttendanceTime;
    private String tuesdayAttendanceTime;
    private String wednesdayAttendanceTime;
    private String thursdayAttendanceTime;
    private String fridayAttendanceTime;

}
