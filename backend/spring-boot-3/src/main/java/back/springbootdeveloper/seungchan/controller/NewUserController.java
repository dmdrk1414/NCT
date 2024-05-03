package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.entity.TempUser;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.NewUserEachResDto;
import back.springbootdeveloper.seungchan.dto.response.NewUsersResDto;
import back.springbootdeveloper.seungchan.service.*;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "신청 유저들의 정보 관련 API", description = "신청 유저들의 정보를 당담한다.")
@RestController
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/new-users")
public class NewUserController {

  private final TempUserService tempUserService;
  private final UserService userService;
  private final TokenService tokenService;
  private final UserUtillService userUtillService;
  private final AttendanceService attendanceService;
  private final PeriodicDataService periodicDataService;
  private final AttendanceTimeService attendanceTimeService;

  @Operation(summary = "신청한 모든 유저 정보 반환", description = "신청한 모든 유저 정보 반환 ")
  @GetMapping("")
  public ResponseEntity<List<NewUsersResDto>> findAllNewUsers() {
    List<NewUsersResDto> newUserList = tempUserService.findAllNewUsers();

    return ResponseEntity.ok().body(newUserList);
  }

  @Operation(summary = "개별 신청 유저 정보 반환", description = "개별 신청 유저의 상세 정보 확인")
  @GetMapping("/{id}")
  public ResponseEntity<NewUserEachResDto> findNewUsers(@PathVariable("id") long id,
      HttpServletRequest request) {
    TempUser tempUser = tempUserService.findNewUsers(id);
    boolean isNuriKingOfToken = tokenService.getNuriKingFromToken(request);

    return ResponseEntity.ok().body(NewUserEachResDto.builder()
        .tempUser(tempUser)
        .isNuriKing(isNuriKingOfToken)
        .build());
  }

  @Operation(summary = "실장의 신청 인원 승락 API", description = "실장의 신청 인원 개별 페이지 승락 버튼 구현")
  @PostMapping("/{id}/acceptance")
  public ResponseEntity<BaseResponseBody> acceptNewUserOfKing(@PathVariable("id") long id,
      HttpServletRequest request) {
    boolean isNuriKing = tokenService.getNuriKingFromToken(request);
    Long idOfNewUser = id;
    if (isNuriKing) {
      TempUser tempUser = tempUserService.removeTempUserById(idOfNewUser);
      UserInfo newUser = UserInfo.getUserFromTempUser(tempUser);
      userService.saveNewUser(newUser);
      userUtillService.saveNewUser(newUser);
      attendanceService.saveNewUser(newUser);
      periodicDataService.saveNewUser(newUser);
      attendanceTimeService.saveNewUser(newUser);
    }

    return BaseResponseBodyUtiil.BaseResponseBodySuccess();
  }

  @Operation(summary = "신청 실원 거절 API", description = "실장의 신청 인원의 개별 신청 거절 API")
  @PostMapping("/{id}/reject")
  public ResponseEntity<BaseResponseBody> rejectNewUserOfKing(@PathVariable("id") long id,
      HttpServletRequest request) {
    boolean isNuriKing = tokenService.getNuriKingFromToken(request);
    Long idOfNewUser = id;
    if (isNuriKing) {
      tempUserService.removeTempUserById(idOfNewUser);
    }

    return BaseResponseBodyUtiil.BaseResponseBodySuccess();
  }
}