package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.SuggestionConstantGroup;
import back.springbootdeveloper.seungchan.dto.request.SuggestionReqDto;
import back.springbootdeveloper.seungchan.dto.response.*;
import back.springbootdeveloper.seungchan.entity.Suggestion;
import back.springbootdeveloper.seungchan.dto.request.SuggestionWriteReqDto;
import back.springbootdeveloper.seungchan.filter.exception.judgment.InvalidSelectionClassificationException;
import back.springbootdeveloper.seungchan.service.SuggestionService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "건의 사항 게시판 API", description = "건의 사항 에 관한 CRUD을 담당한다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
@RequestMapping("/suggestion")
public class SuggestionsController {
    private final SuggestionService suggestionService;
    private final TokenService tokenService;
    private final UserUtillService userUtillService;

    @Operation(summary = "건의 게시판 작성", description = "개인 회원이 건의 게시판을 작성을 한다.")
    @PostMapping("/write")
    public ResponseEntity<BaseResponseBody> writeSuggestion(@Valid @RequestBody SuggestionWriteReqDto suggestionWriteRequest) {
        Boolean isValidClassification = SuggestionConstantGroup.classification.contain(suggestionWriteRequest.getClassification());
        if (isValidClassification == false) {
            throw new InvalidSelectionClassificationException();
        }
        suggestionService.save(suggestionWriteRequest);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }

    @Operation(summary = "건의 게시판 조회", description = "건의 게시판 조회. 비밀 게시판 실장만 조회 가능")
    @GetMapping("")
    public ResponseEntity<SuggestionsResultResponse> fetchSuggestions(HttpServletRequest request) {
        Long userIdOfSearch = tokenService.getUserIdFromToken(request);
        boolean isNuriKing = tokenService.getNuriKingFromToken(request);
        List<Suggestion> suggestions = suggestionService.findAll()
                .stream()
                // blogService에서 찾아온 Article의 하나하나가 파라미터로 넘어간다.
                .map(Suggestion::new)
                .toList();
        SuggestionsResultResponse suggestionsResultResponse = new SuggestionsResultResponse(suggestions, isNuriKing);

        return ResponseEntity.ok().body(suggestionsResultResponse);
    }

    @Operation(summary = "각각의 건의 게시판 조회", description = "각각의 건의 게시판의 조회를 한다.")
    @GetMapping("{id}")
    public ResponseEntity<EachSuggestionsResDto> fetchSuggestions(HttpServletRequest request, @PathVariable(value = "id") Long id) {
        Suggestion suggestions = suggestionService.findById(id);
        EachSuggestionsResDto eachSuggestionsResDto = new EachSuggestionsResDto(suggestions);

        return ResponseEntity.ok().body(eachSuggestionsResDto);
    }

    @Operation(summary = "건의 게시판 확인 버튼", description = "건의 게시판 확인 버튼을 생성한다.")
    @PostMapping("/check")
    public ResponseEntity<SuggestionCheckResDto> checkSuggestions(@RequestBody SuggestionReqDto suggestionReqDto, HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);
        Long suggestionId = suggestionReqDto.getSuggestionId();
        Boolean check = suggestionService.checkToggle(suggestionId);

        return ResponseEntity.ok().body(SuggestionCheckResDto.builder()
                .check(check)
                .build());
    }
}
