package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.Suggestions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EachSuggestionsResDto {
    private Long id;
    private String classification;
    private String title;
    private boolean isCheck;
    private String holidayPeriod;

    public EachSuggestionsResDto(Suggestions suggestions) {
        this.id = suggestions.getId();
        this.classification = suggestions.getClassification();
        this.title = suggestions.getTitle();
        this.isCheck = suggestions.isCheck();
        this.holidayPeriod = suggestions.getHolidayPeriod();
    }
}
