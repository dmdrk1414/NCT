package back.springbootdeveloper.seungchan.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttendancePassResDto {

  private boolean isPassAtNow;

  @Builder
  public AttendancePassResDto(boolean isPassAtNow) {
    this.isPassAtNow = isPassAtNow;
  }
}
