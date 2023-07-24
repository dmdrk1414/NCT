package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.Suggestions;
import back.springbootdeveloper.seungchan.dto.request.SuggestionWriteRequest;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.ResponseSuggestion;
import back.springbootdeveloper.seungchan.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class SuggestionsController {
    private final SuggestionService suggestionService;

    @PostMapping("/suggestions/write")
    public ResponseEntity<BaseResponseBody> writeSuggestion(@RequestBody SuggestionWriteRequest suggestionWriteRequest) {
        Suggestions suggestions = suggestionService.save(suggestionWriteRequest);
        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 200), HttpStatus.OK);
    }

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
