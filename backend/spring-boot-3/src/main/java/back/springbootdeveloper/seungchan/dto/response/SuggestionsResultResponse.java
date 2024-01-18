package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.Suggestion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SuggestionsResultResponse {
    private List<Suggestion> suggestionLists;
    private boolean isNuriKing;

    public SuggestionsResultResponse(List<Suggestion> suggestionLists, boolean isNuriKing) {
        this.suggestionLists = suggestionLists;
        this.isNuriKing = isNuriKing;
    }
}
