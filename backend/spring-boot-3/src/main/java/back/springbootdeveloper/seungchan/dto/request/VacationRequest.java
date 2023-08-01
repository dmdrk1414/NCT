package back.springbootdeveloper.seungchan.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacationRequest {
    private String[] preVacationDate;
    private int cntUseOfVacation;
}
