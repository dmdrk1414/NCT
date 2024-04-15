package back.springbootdeveloper.seungchan.dto.request;

import back.springbootdeveloper.seungchan.entity.Suggestion;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SuggestionWriteReqDto {

  @NotBlank(message = "{validation.suggestion.classification.notblank}")
  // controller에서 추가 검증 체크
  private String classification;

  @NotBlank(message = "{validation.suggestion.title.notblank}")
  private String title;

  private String holidayPeriod;

  public Suggestion toEntity() {
    return Suggestion.builder()
        .classification(classification)
        .title(title)
        .holidayPeriod(holidayPeriod)
        .build();
  }
}
