package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.Suggestions;
import back.springbootdeveloper.seungchan.dto.request.SuggestionWriteRequest;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.SuggestionList;
import back.springbootdeveloper.seungchan.dto.response.SuggestionsResultResponse;
import back.springbootdeveloper.seungchan.service.SuggestionService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final TokenService tokenService;
    private final UserUtillService userUtillService;

    @PostMapping("/suggestions/write")
    public ResponseEntity<BaseResponseBody> writeSuggestion(@RequestBody SuggestionWriteRequest suggestionWriteRequest) {
        Suggestions suggestions = suggestionService.save(suggestionWriteRequest);
        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 200), HttpStatus.OK);
    }

    @GetMapping("/suggestions")
    public ResponseEntity<SuggestionsResultResponse> fetchSuggestions(HttpServletRequest request) {
        Long userIdOfSearch = tokenService.getUserIdFromToken(request);
        boolean isNuriKing = userUtillService.isNuriKing(userIdOfSearch);
        List<SuggestionList> suggestionLists = suggestionService.findAll()
                .stream()

                // blogService에서 찾아온 Article의 하나하나가 파라미터로 넘어간다.
                .map(SuggestionList::new)
                .toList();
        SuggestionsResultResponse suggestionsResultResponse = new SuggestionsResultResponse(suggestionLists, isNuriKing);

        return ResponseEntity.ok().body(suggestionsResultResponse);
    }
}
