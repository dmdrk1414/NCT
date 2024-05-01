package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VacationNumberReqDto {

  //  @NotBlank(message = "{validation.vacation.count}")
  private Integer vacationToken;
}
