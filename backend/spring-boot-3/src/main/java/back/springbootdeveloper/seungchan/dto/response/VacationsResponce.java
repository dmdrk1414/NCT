package back.springbootdeveloper.seungchan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VacationsResponce {

  private List<String> absences;
  private List<String> beforeVacationDate;
  private List<String> preVacationDate;

  public VacationsResponce(List<String> absences, List<String> beforeVacationDate,
      List<String> preVacationDate) {
    this.absences = absences;
    this.beforeVacationDate = beforeVacationDate;
    this.preVacationDate = preVacationDate;
  }
}
