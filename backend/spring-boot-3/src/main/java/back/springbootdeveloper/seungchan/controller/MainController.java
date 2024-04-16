package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.RESPONSE_MESSAGE;
import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.domain.ObUser;
import back.springbootdeveloper.seungchan.dto.request.UserEachAttendanceControlReqDto;
import back.springbootdeveloper.seungchan.dto.response.*;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.entity.UserUtill;
import back.springbootdeveloper.seungchan.domain.YbUserInfomation;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.UserUtilRepository;
import back.springbootdeveloper.seungchan.service.*;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import back.springbootdeveloper.seungchan.util.BaseResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Tag(name = "main page API", description = "로그인을 한후의 main page이다. yb, ob의 정보을 얻을 수 있다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
@RequestMapping("/main")
public class MainController {

  private final UserService userServiceImp;
  private final UserUtilRepository userUtilRepository;
  private final UserOfMainService userOfMainService;
  private final UserUtillService userUtillService;
  private final TokenService tokenService;
  private final AttendanceService attendanceService;
  private final AttendanceTimeService attendanceTimeService;
  private final DatabaseService databaseService;
  private final NoticeService noticeService;

  @Operation(summary = "main page 현재 회원 인원들의 정보들을 찾는다.", description = "현재 회원 인원들의 정보들을 위한 api 찾는다.")
  @GetMapping("/ybs")
  public ResponseEntity<YbUserListResponse> findAllYbUsers(HttpServletRequest request) {
    Long userId = tokenService.getUserIdFromToken(request);
    boolean isPassAttendance = attendanceService.isPassAttendanceAtToday(userId);
    boolean isObUser = false;
    List<YbUserInfomation> ybUserInfomationList = userOfMainService.findAllByIsOb(isObUser);

    return ResponseEntity.ok().body(YbUserListResponse.builder()
        .ybUserInfomationList(ybUserInfomationList)
        .isPassAttendanceOfSearchUse(isPassAttendance)
        .build());
  }

  @Operation(summary = "main page 졸업 인원들의 정보", description = "main page 졸업 인원들의 정보들을 나열, 실장들과 일반인들이 볼수 있는 정보가 나누어져 있다.")
  @GetMapping("/obs")
  public ResponseEntity<List<ObUserOfMainResponse>> findAllObUser(HttpServletRequest request) {
    Long userId = tokenService.getUserIdFromToken(request);
    boolean isNuriKing = tokenService.getNuriKingFromToken(request);
    List<ObUser> obUserList = userOfMainService.findAllObUser();

    return ResponseEntity.ok()
        .body(Collections.singletonList(new ObUserOfMainResponse(obUserList, isNuriKing)));
  }

  @Operation(summary = "main page 개인 회원 정보 상세 조회", description = "회원 정보 실장과 일반 회원 권한에 따른 조회 정보가 다르다.")
  @GetMapping("/detail/{id}")
  public ResponseEntity<UserOfDetail2MainResponse> fetchUserOfDetail2Main(
      HttpServletRequest request, @PathVariable long id) {
    Long userIdOfSearch = tokenService.getUserIdFromToken(request);
    UserInfo user = userServiceImp.findUserById(id);

    UserUtill userUtill = userUtilRepository.findByUserId(userIdOfSearch);

    UserOfDetail2MainResponse response = new UserOfDetail2MainResponse(userUtill, user);

    return ResponseEntity.ok().body(response);
  }

  @Operation(summary = "회원들의 개인 각각의 출석시간 Find", description = "유저의 출석시간을 변경하기위한 컨트롤러 기본 09시에서 임의대로 설정가능하다.")
  @GetMapping("/detail/{id}/control")
  public ResponseEntity<UserControlResDto> userControlFindInfo(HttpServletRequest request,
      @PathVariable long id) {
    UserControlResDto userControlResDto = attendanceTimeService.findUserControlResById(id);

    return ResponseEntity.ok().body(userControlResDto);
  }

  @Operation(summary = "유저의 개인적 출석 시간 변경", description = "유저의 개인적 출석 시간 월, 화, 수, 목, 금 요일을 개별적으로 변경한다.")
  @PostMapping("/detail/{id}/control")
  public ResponseEntity<BaseResponseBody> userControlPostInfo(
      @Valid @RequestBody UserEachAttendanceControlReqDto userEachAttendanceControlRequest,
      @PathVariable("id") long id) {
    attendanceTimeService.updateAttendanceTime(userEachAttendanceControlRequest, id);

    return BaseResponseBodyUtiil.BaseResponseBodySuccess();
  }

  @Operation(summary = "개별 장기 휴가 신청", description = "장기 휴가 신청을 할시 장기 휴가 신청이 완료가 된다.")
  @PostMapping("/detail/{id}/control/exception/attendance")
  public ResponseEntity<BaseResponseBody> userExceptionAttendanceControl(@PathVariable long id) {
    attendanceTimeService.updateExceptionAttendance(id);

    return BaseResponseBodyUtiil.BaseResponseBodySuccess();
  }

  @Operation(summary = "개별 장기 휴가 신청 확인", description = "장기 휴가 신청을 할시 장기 휴가 신청 여부를 확인 한다.")
  @GetMapping("/detail/{id}/control/exception/attendance")
  public ResponseEntity<AttendanceTimeExceptionAttendanceResponse> userFindExceptionAttendanceControl(
      @PathVariable long id) {
    boolean isExceptionAttendance = attendanceTimeService.findExceptionAttendance(id);

    return ResponseEntity.ok().body(AttendanceTimeExceptionAttendanceResponse.builder()
        .isExceptionAttendance(isExceptionAttendance)
        .build());
  }

  @Operation(summary = "회원 추방 기능", description = "실장의 회원 추방 기능 추가.")
  @PostMapping("/detail/{id}/control/delete")
  public ResponseEntity<BaseResponseBody> deleteUserControl(
      HttpServletRequest request,
      @PathVariable long id) {
    final Long myUserId = tokenService.getUserIdFromToken(request);
    final UserInfo myUser = userServiceImp.findUserById(myUserId);

    // 기능 사용자 실장 유무 확인
    Boolean myUserIsNuriKing = userUtillService.isNuriKing(myUserId);
    if (!myUserIsNuriKing) {
      return BaseResponseBodyUtiil.BaseResponseBodyBad(ResponseMessage.BAD_NOT_USER_NOMAL.get());
    }

    // 졸업 유저 유무
    Boolean isGraduation = userServiceImp.idGraduationUser(id);
    if (isGraduation) {
      return BaseResponseBodyUtiil.BaseResponseBodyBad(
          ResponseMessage.BAD_IS_GRADUATION_USER.get());
    }

    // 실장 유무 확인
    Boolean targetIsNuriKing = userUtillService.isNuriKing(id);
    if (targetIsNuriKing) {
      return BaseResponseBodyUtiil.BaseResponseBodyBad(ResponseMessage.BAD_IS_NURI_KING.get());
    }

    databaseService.deleteUser(id);
    return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.SUCCESS_DELETE_USER.get());
  }

  @Operation(summary = "회원 졸업-일반 전환 기능", description = "실장이 개인 회원 졸업-일반 전환 기능")
  @PostMapping("/detail/{id}/control/toggle/grade")
  public ResponseEntity<BaseResponseBody> togleGradeUserControl(
      @PathVariable long id) {
    final UserInfo targetUser = userServiceImp.findUserById(id);
    final String targetUserName = targetUser.getName();
    final Boolean GRADUATION_USER = true;
    final Boolean NOMAL_USER = false;

    // 실장 유무 확인
    Boolean targetIsNuriKing = userUtillService.isNuriKing(id);
    if (targetIsNuriKing) {
      return BaseResponseBodyUtiil.BaseResponseBodyBad(ResponseMessage.BAD_IS_NURI_KING.get());
    }

    Boolean isGraduation = userServiceImp.idGraduationUser(id);
    // 일반 회원 -> 졸업 토글 기능
    if (!isGraduation) {
      userServiceImp.toggleGrade(id, GRADUATION_USER);
      return BaseResponseBodyUtiil.BaseResponseBodySuccess(
          RESPONSE_MESSAGE.SUCCESE_TOGLE_GRADUATION(targetUserName));
    }
    // 졸업 회원 -> 일반 토글 기능
    if (isGraduation) {
      userServiceImp.toggleGrade(id, NOMAL_USER);
      return BaseResponseBodyUtiil.BaseResponseBodySuccess(
          RESPONSE_MESSAGE.SUCCESE_TOGLE_MEMBER(targetUserName));
    }

    return BaseResponseBodyUtiil.BaseResponseBodySuccess(
        RESPONSE_MESSAGE.BAD_TOGGLE_MEMBER_GRADE(targetUserName));
  }

  @Operation(summary = "실장 권한 주는 기능", description = "실장이 개인 회원에게 실장 권한 전환 기능")
  @PostMapping("/detail/{id}/control/give/king")
  public ResponseEntity<BaseResponseBody> giveKing2UserControl(
      HttpServletRequest request,
      @PathVariable long id) {
    final UserInfo targetUser = userServiceImp.findUserById(id);
    final String targetUserName = targetUser.getName();
    final Long myUserId = tokenService.getUserIdFromToken(request);
    final UserInfo myUser = userServiceImp.findUserById(myUserId);

    // 실장 유무 확인
    Boolean targetIsNuriKing = userUtillService.isNuriKing(id);
    if (targetIsNuriKing) {
      return BaseResponseBodyUtiil.BaseResponseBodyBad(ResponseMessage.BAD_IS_NURI_KING.get());
    }

    // 타겟 유저 졸업 유저 유무
    Boolean isGraduation = userServiceImp.idGraduationUser(id);
    if (isGraduation) {
      return BaseResponseBodyUtiil.BaseResponseBodyBad(
          ResponseMessage.BAD_NOT_GIVE_KING_GRADUATION_USER.get());
    }

    // 실장 권한 주기
    Boolean NURI_KING = true;
    userUtillService.updateUserGrade(targetUser.getId(), NURI_KING);
    // 내 권한 삭제
    Boolean NOMAL_USER = false;
    userUtillService.updateUserGrade(myUser.getId(), NOMAL_USER);

    return BaseResponseBodyUtiil.BaseResponseBodySuccess(
        RESPONSE_MESSAGE.SUCCESE_GIVE_KING(targetUserName, myUser.getName()));
  }


  @Operation(summary = "공지사항 - 조회", description = "main페이지의 공지사항 조회")
  @GetMapping(value = "/notices")
  public BaseResultDTO<NoticesResDto> findClubArticleDetail(
      HttpServletRequest request) {
    List<NoticeInformation> noticeInformations = noticeService.getNoticeInformations();

    return BaseResultDTO.ofSuccess(NoticesResDto.builder()
        .noticeInformations(noticeInformations)
        .build());
  }
}
