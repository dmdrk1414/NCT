package back.springbootdeveloper.seungchan.dto.request;

import back.springbootdeveloper.seungchan.entity.AttendanceTime;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEachAttendanceControlReqDto {

  @NotBlank(message = "{validation.update.attendance.time.notblank}")
  @Pattern(regexp = "^(0[1-9]|1[0-9]|2[0-3])$", message = "{validation.update.attendance.time.invalid}")
  private String mondayAttendanceTime;

  @NotBlank(message = "{validation.update.attendance.time.notblank}")
  @Pattern(regexp = "^(0[1-9]|1[0-9]|2[0-3])$", message = "{validation.update.attendance.time.invalid}")
  private String tuesdayAttendanceTime;

  @NotBlank(message = "{validation.update.attendance.time.notblank}")
  @Pattern(regexp = "^(0[1-9]|1[0-9]|2[0-3])$", message = "{validation.update.attendance.time.invalid}")
  private String wednesdayAttendanceTime;

  @NotBlank(message = "{validation.update.attendance.time.notblank}")
  @Pattern(regexp = "^(0[1-9]|1[0-9]|2[0-3])$", message = "{validation.update.attendance.time.invalid}")
  private String thursdayAttendanceTime;

  @NotBlank(message = "{validation.update.attendance.time.notblank}")
  @Pattern(regexp = "^(0[1-9]|1[0-9]|2[0-3])$", message = "{validation.update.attendance.time.invalid}")
  private String fridayAttendanceTime;

}
