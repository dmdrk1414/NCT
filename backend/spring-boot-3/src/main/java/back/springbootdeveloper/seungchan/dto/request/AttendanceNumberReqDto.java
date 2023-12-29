package back.springbootdeveloper.seungchan.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AttendanceNumberReqDto {
    private String numOfAttendance;
}
