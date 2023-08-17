package back.springbootdeveloper.seungchan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VacationRequest {
    private String[] preVacationDate;
    private int cntUseOfVacation;
}
