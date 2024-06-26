package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.TempUserFormReqDto;
import back.springbootdeveloper.seungchan.dto.request.UserLoginRequest;
import back.springbootdeveloper.seungchan.dto.response.SignNewUserResDto;
import back.springbootdeveloper.seungchan.dto.response.UserLoginResponse;
import back.springbootdeveloper.seungchan.service.LoginService;
import back.springbootdeveloper.seungchan.service.TempUserService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인, 신입 가입 신청 관련 API", description = "로그인, 신입의 가입 신청 관리한다.")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class LoginPageController {

  private final UserService userService;
  private final TempUserService tempUserService;
  private final LoginService loginService;
  private final TokenService tokenService;


  @Operation(summary = "신입 가입 신청", description = "신입 가입 신청을 하지만 회원 가입이 아님을 인지하자. TempUser에 저장")
  @PostMapping("/sign")
  public ResponseEntity<SignNewUserResDto> userSignFrom(
      @RequestBody @Valid TempUserFormReqDto requestUserForm) {
    String email = requestUserForm.getEmail();

    tempUserService.save(requestUserForm);
    Boolean existNewUser = tempUserService.exist(email);

    // 등록 완료 되었다는 boolean 리턴
    return ResponseEntity.ok().body(SignNewUserResDto.builder()
        .isApply(existNewUser)
        .build());
  }

  @Operation(summary = "로그인", description = "기존 회원들의 로그인이다")
  @PostMapping("/login")
  public ResponseEntity<UserLoginResponse> userLogin(
      @RequestBody @Valid UserLoginRequest userLoginRequest, HttpServletRequest request,
      HttpServletResponse response) {
    UserLoginResponse userLoginResponse = loginService.login(userLoginRequest, request, response);

    return ResponseEntity.ok().body(userLoginResponse);
  }
}
