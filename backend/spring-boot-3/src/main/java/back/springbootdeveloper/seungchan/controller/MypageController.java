package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.dto.request.UpdateUserFormReqDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.MyPageResDto;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "내 정보 (mypage) API ", description = "mypage의 정보에 관한 CRUD을 담당한다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
@RequestMapping("/mypage")
public class MypageController {

  private final UserService userServiceImp;
  private final TokenService tokenService;

  @Operation(summary = "현제 본인 회원 자신의 정보 조회", description = "현제 본인 회원 자신의 상제 정보 조회")
  @GetMapping("")
  public ResponseEntity<MyPageResDto> findMypage(HttpServletRequest request) {
    Long id = tokenService.getUserIdFromToken(request);
    UserInfo user = userServiceImp.findUserById(id);

    return ResponseEntity.ok().body(new MyPageResDto(user));
  }

  @Operation(summary = "현제 회원 본인 정보 업데이트", description = "현제 본인 회원 상세 정보 업데이트")
  @PutMapping("/update")
  public ResponseEntity<BaseResponseBody> updateMyInformation(
      @Valid @RequestBody UpdateUserFormReqDto updateUserFormRequest, HttpServletRequest request) {
    Long userId = tokenService.getUserIdFromToken(request);
    userServiceImp.updateUser(updateUserFormRequest.toEntity(), userId);

    return BaseResponseBodyUtiil.BaseResponseBodySuccess();
  }

  @Operation(summary = "업데이트 페이지 현제 회원 본인 정보 조회.", description = "업데이트 페이지 현제 본인 회원 상세 정보 업데이트")
  @GetMapping("/update")
  public ResponseEntity<MyPageResDto> findMyUpdatePage(HttpServletRequest request) {
    Long userId = tokenService.getUserIdFromToken(request);
    UserInfo userInfo = userServiceImp.findUserById(userId);

    return ResponseEntity.ok().body(new MyPageResDto(userInfo));
  }
}
