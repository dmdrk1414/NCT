package back.springbootdeveloper.seungchan.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacationCountRequest {
    private int vacationCount;
    private Long userId;
}
