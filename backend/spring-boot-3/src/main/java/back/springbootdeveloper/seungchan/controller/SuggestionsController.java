package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.response.ResponseSuggestion;
import back.springbootdeveloper.seungchan.service.SuggestionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class SuggestionsController {
    private final SuggestionService suggestionService;

    @GetMapping("/suggestions")
    public ResponseEntity<List<ResponseSuggestion>> fetchSuggestions() {
        List<ResponseSuggestion> suggestions = suggestionService.findAll()
                .stream()

                // blogService에서 찾아온 Article의 하나하나가 파라미터로 넘어간다.
                .map(ResponseSuggestion::new)
                .toList();

        return ResponseEntity.ok().body(suggestions);
    }
}
