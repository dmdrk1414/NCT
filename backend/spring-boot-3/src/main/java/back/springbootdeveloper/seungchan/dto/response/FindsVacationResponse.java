package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindsVacationResponse {

  int cntVacation;
  private List<String> absences;
  private List<String> beforeVacationDate;
  private List<String> preVacationDate;

  public FindsVacationResponse(int cntVacation, VacationsResponce vacationsResponce) {
    this.cntVacation = cntVacation;
    this.absences = vacationsResponce.getAbsences();
    this.beforeVacationDate = vacationsResponce.getBeforeVacationDate();
    this.preVacationDate = vacationsResponce.getPreVacationDate();
  }
}
