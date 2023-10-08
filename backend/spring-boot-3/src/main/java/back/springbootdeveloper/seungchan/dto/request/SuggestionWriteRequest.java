package back.springbootdeveloper.seungchan.dto.request;

import back.springbootdeveloper.seungchan.entity.Suggestions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuggestionWriteRequest {
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
