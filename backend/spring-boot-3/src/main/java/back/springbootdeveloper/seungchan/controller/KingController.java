package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.NoticesReqDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.entity.Notice;
import back.springbootdeveloper.seungchan.service.NoticeService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserUtillService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import back.springbootdeveloper.seungchan.util.MyValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "공지 사항 관련 컨트롤러", description = "실장 페이지의 공지 사항 관련 컨트롤러")
@RestController
@RequestMapping("/control")
@RequiredArgsConstructor
public class KingController {

  private final NoticeService noticeService;
  private final UserUtillService userUtillService;
  private final TokenService tokenService;

  @Operation(summary = "공지 사항 등록 API", description = "실장 공지 사항 등록 API")
  @PostMapping("/notices/write")
  public ResponseEntity<BaseResponseBody> writeSuggestion(
      @Valid @RequestBody NoticesReqDto noticesWriteReqDto,
      HttpServletRequest request) {
    // 실장검증
    MyValidation.isLeaderMember(tokenService, request);

    // 공지 사항 등록
    Notice notice = noticeService.save(noticesWriteReqDto);

    return BaseResponseBodyUtiil.BaseResponseBodySuccess(
        ResponseMessage.SUCCESS_WRITE_NOTICE.get());
  }

  @Operation(summary = "공지 사항 삭제 API", description = "실장 공지 사항 삭제 API")
  @DeleteMapping("/notices/{notice_id}")
  public ResponseEntity<BaseResponseBody> deleteNotice(
      @PathVariable(value = "notice_id") Long noticeId,
      HttpServletRequest request) {
    // 실장검증
    MyValidation.isLeaderMember(tokenService, request);

    // 공지 사항 삭제
    if (noticeService.delete(noticeId)) {
      return BaseResponseBodyUtiil.BaseResponseBodySuccess(
          ResponseMessage.SUCCESS_DELETE_NOTICE.get());
    }

    return BaseResponseBodyUtiil.BaseResponseBodyBad(ResponseMessage.BAD_DELETE_NOTICE.get());
  }

  @Operation(summary = "공지 사항 수정 API", description = "실장 공지 사항 수정 API")
  @PutMapping("/notices/{notice_id}")
  public ResponseEntity<BaseResponseBody> updateNotice(
      @PathVariable(value = "notice_id") Long noticeId,
      @RequestBody @Valid NoticesReqDto noticesUpdateReqDto,
      HttpServletRequest request) {
    // 실장검증
    MyValidation.isLeaderMember(tokenService, request);

    // 공지 사항 삭제
    if (noticeService.update(noticeId, noticesUpdateReqDto)) {
      return BaseResponseBodyUtiil.BaseResponseBodySuccess(
          ResponseMessage.SUCCESS_UPDATE_NOTICE.get());
    }

    return BaseResponseBodyUtiil.BaseResponseBodyBad(ResponseMessage.BAD_UPDATE_NOTICE.get());
  }
}
