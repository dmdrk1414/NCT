package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.UpdateClubArticlePutDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.ClubArticleDetailResDto;
import back.springbootdeveloper.seungchan.service.ClubArticleService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Club Article 게시물 관련 Controller", description = "Club Article 관련 API을 관리한다.")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clubs/informations/{club_id}/articles")
@RequiredArgsConstructor
public class ClubArticleController {
    private final ClubArticleService clubArticleService;

    @Operation(summary = "팀 게시판 - 수정 API", description = "글 작성자의 게시물을 업데이트한다.")
    @PutMapping(value = "/{article_id}")
    public ResponseEntity<BaseResponseBody> updateClubArticle(
            @RequestBody @Valid UpdateClubArticlePutDto updateClubArticlePutDto,
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        if (clubArticleService.isAuthor(memberId, clubId, articleId)) {
            clubArticleService.updateClubArticle(clubId, memberId, articleId, updateClubArticlePutDto);

            return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.UPDATE_CLUB_ARTICLE.get());
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_UPDATE_CLUB_ARTICLE.get());
    }

    @Operation(summary = "팀 게시판 - 삭제 API", description = "글 작성자의 게시물을 삭제 한다.")
    @DeleteMapping(value = "/{article_id}")
    public ResponseEntity<BaseResponseBody> deleteClubArticle(
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        if (clubArticleService.isAuthor(memberId, clubId, articleId)) {
            clubArticleService.deleteClubArticle(clubId, memberId, articleId);

            return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.SUCCESS_DELETE_CLUB_ARTICLE.get());
        }
        return BaseResponseBodyUtiil.BaseResponseBodyFailure(ResponseMessage.BAD_DELETE_CLUB_ARTICLE.get());
    }

    @Operation(summary = "팀 게시판 - 상세 페이지 - 조회", description = "해당 club 게시판의 상세 페이지 조회")
    @GetMapping(value = "/{article_id}")
    public BaseResultDTO<ClubArticleDetailResDto> findClubArticleDetail(
            @PathVariable(value = "club_id") Long clubId,
            @PathVariable(value = "article_id") Long articleId) {
        // TODO: 2/24/24 token으로 memberId 얻기
        Long memberId = 1L;
        final ClubArticleDetailResDto clubArticleDetailResDto = clubArticleService.getClubArticleDetailResDto(clubId, articleId, memberId);

        return BaseResultDTO.ofSuccess(clubArticleDetailResDto);
    }
}
