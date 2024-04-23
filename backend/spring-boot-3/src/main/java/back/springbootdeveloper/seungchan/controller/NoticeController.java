package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.SuggestionConstantGroup;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.NoticesWriteReqDto;
import back.springbootdeveloper.seungchan.dto.request.SuggestionWriteReqDto;
import back.springbootdeveloper.seungchan.dto.response.AttendanceNumberResDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.entity.Notice;
import back.springbootdeveloper.seungchan.entity.NumOfTodayAttendence;
import back.springbootdeveloper.seungchan.filter.exception.judgment.InvalidSelectionClassificationException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "공지 사항 관련 컨트롤러", description = "실장 페이지의 공지 사항 관련 컨트롤러")
@RestController
@RequestMapping("/control/notices")
@RequiredArgsConstructor
public class NoticeController {

  private final NoticeService noticeService;
  private final UserUtillService userUtillService;
  private final TokenService tokenService;

  @Operation(summary = "공지 사항 등록 API", description = "실장 공지 사항 등록 API")
  @PostMapping("/write")
  public ResponseEntity<BaseResponseBody> writeSuggestion(
      @Valid @RequestBody NoticesWriteReqDto noticesWriteReqDto,
      HttpServletRequest request) {
    // 실장검증
    MyValidation.isLeaderMember(tokenService, request);

    // 공지 사항 등록
    Notice notice = noticeService.save(noticesWriteReqDto);

    return BaseResponseBodyUtiil.BaseResponseBodySuccess(
        ResponseMessage.SUCCESS_WRITE_NOTICE.get());
  }
}
