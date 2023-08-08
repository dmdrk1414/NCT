package back.springbootdeveloper.seungchan.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SuggestionsResultResponse {
    private List<SuggestionList> suggestionLists;
    private boolean isNuriKing;

    public SuggestionsResultResponse(List<SuggestionList> suggestionLists, boolean isNuriKing) {
        this.suggestionLists = suggestionLists;
        this.isNuriKing = isNuriKing;
    }
}
