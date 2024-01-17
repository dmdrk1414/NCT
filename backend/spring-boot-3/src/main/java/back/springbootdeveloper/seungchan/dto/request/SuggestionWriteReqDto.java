package back.springbootdeveloper.seungchan.dto.request;

import back.springbootdeveloper.seungchan.entity.Suggestions;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SuggestionWriteReqDto {
    private String classification;
    private String title;
    private String holidayPeriod;

    public Suggestions toEntity() {
        return Suggestions.builder()
                .classification(classification)
                .title(title)
                .holidayPeriod(holidayPeriod)
                .build();
    }
}
