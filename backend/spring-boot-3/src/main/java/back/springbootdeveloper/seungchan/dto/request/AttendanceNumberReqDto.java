package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AttendanceNumberReqDto {
    @NotBlank(message = "{validation.attendanceNumber.notblank}")
    @Size(min = 4, message = "{validation.attendanceNumber.size.min.4}")
    private String numOfAttendance;
}
