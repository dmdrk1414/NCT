package back.springbootdeveloper.seungchan.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttendanceNumberResponse {
    private boolean isPassAtNow;

    @Builder
    public AttendanceNumberResponse(boolean isPassAtNow) {
        this.isPassAtNow = isPassAtNow;
    }
}
