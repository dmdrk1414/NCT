package back.springbootdeveloper.seungchan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VacationCountRequest {
    private int vacationCount;
    private Long userId;
}
