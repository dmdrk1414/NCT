package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.TeamArticleUpdateReqDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.service.TeamArticleService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "팀 게시판", description = "팀 게시판 관련 CRUD 작업")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clubs/informations")
public class TeamArticleController {
    private final TeamArticleService teamArticleService;

    @Operation(summary = "팀 게시판 - 수정", description = "팀 게시판 수정을 한다.")
    @PutMapping("/articles/{article_id}")
    public ResponseEntity<BaseResponseBody> updateTeamArticle(@RequestBody @Valid TeamArticleUpdateReqDto teamArticleUpdateReqDto, @PathVariable("article_id") Long articleId, HttpServletRequest request) {
        teamArticleService.updateArticle(teamArticleUpdateReqDto, articleId);

//        teamArticleService.updateArticle(teamArticleUpdateReqDto.getClubArticleUpdateTitle(),
//                teamArticleUpdateReqDto.getClubArticleUpdateCotent());
        return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.UPDATE_TEAM_ARTICLE_MESSAGE.get());
    }
}
