package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class AttendanceNumberResDto {

  private String attendanceNum;
  private String dayAtNow;
}
