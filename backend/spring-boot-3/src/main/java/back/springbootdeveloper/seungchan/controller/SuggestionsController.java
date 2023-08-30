package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.entity.Suggestions;
import back.springbootdeveloper.seungchan.dto.request.SuggestionWriteRequest;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.SuggestionList;
import back.springbootdeveloper.seungchan.dto.response.SuggestionsResultResponse;
import back.springbootdeveloper.seungchan.service.SuggestionService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "건의 사항 게시판 API", description = "건의 사항 에 관한 CRUD을 담당한다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
@RequestMapping("/suggestions")
public class SuggestionsController {
    private final SuggestionService suggestionService;
    private final TokenService tokenService;
    private final UserUtillService userUtillService;

    @Operation(summary = "건의 게시판 작성", description = "건의 게시판을 작성을 한다. 건의, 비밀, 자유, 휴가의 분류가 있으며 비밀은 실장만 조회가 가능하다.")
    @PostMapping("/write")
    public ResponseEntity<BaseResponseBody> writeSuggestion(@RequestBody SuggestionWriteRequest suggestionWriteRequest) {
        Suggestions suggestions = suggestionService.save(suggestionWriteRequest);
        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 200), HttpStatus.OK);
    }

    @Operation(summary = "건의 게시판 조회", description = "건의 게시판의 조회를 한다. 비밀 게시판은 실장 만의 조회가 가능하다.")
    @GetMapping("")
    public ResponseEntity<SuggestionsResultResponse> fetchSuggestions(HttpServletRequest request) {
        Long userIdOfSearch = tokenService.getUserIdFromToken(request);
        boolean isNuriKing = tokenService.getNuriKingFromToken(request);
        List<SuggestionList> suggestionLists = suggestionService.findAll()
                .stream()

                // blogService에서 찾아온 Article의 하나하나가 파라미터로 넘어간다.
                .map(SuggestionList::new)
                .toList();
        SuggestionsResultResponse suggestionsResultResponse = new SuggestionsResultResponse(suggestionLists, isNuriKing);

        return ResponseEntity.ok().body(suggestionsResultResponse);
    }
}
