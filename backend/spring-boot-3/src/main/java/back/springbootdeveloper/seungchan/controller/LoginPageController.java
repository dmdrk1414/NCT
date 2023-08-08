package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.dto.request.UserLoginRequest;
import back.springbootdeveloper.seungchan.dto.request.RequestUserForm;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.dto.response.UserLoginResponse;
import back.springbootdeveloper.seungchan.service.LoginService;
import back.springbootdeveloper.seungchan.service.TokenService;
import back.springbootdeveloper.seungchan.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ResponseBody
public class LoginPageController {
    private final UserService userService;
    private final LoginService loginService;
    private final TokenService tokenService;


    @PostMapping("/sign")
    public ResponseEntity<BaseResponseBody> userSignFrom(@RequestBody RequestUserForm requestUserForm) {
        User user = userService.save(requestUserForm);

        return new ResponseEntity<>(new BaseResponseBody("SUCCESS", 200), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) {
        UserLoginResponse userLoginResponse = loginService.login(userLoginRequest, request, response);

        return ResponseEntity.ok().body(userLoginResponse);
    }
}
