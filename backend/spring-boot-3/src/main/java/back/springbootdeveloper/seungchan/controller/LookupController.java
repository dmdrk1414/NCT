package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.dto.request.FindPasswordReqDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.filter.exception.user.UserNotExistException;
import back.springbootdeveloper.seungchan.service.AuthenticationEmailService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원들의 정보를 찾는 컨트롤러", description = "회원들의 정보를 입력하고, 정보를 찾는 기능들의 모임")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/admin")
public class LookupController {
    private final UserService userService;
    private final AuthenticationEmailService authenticationEmailService;

    @Operation(summary = "admin page 비밀 번호 찾기 api ", description = "이메일 인증을 통해 해당 이메일로 비밀번호를 보낸다.")
    @PostMapping("/find/password")
    public ResponseEntity<BaseResponseBody> findPasswordController(@RequestBody @Valid FindPasswordReqDto findPasswordReqDto) {
        // 1. 원하는 정보로 유저가 없으면
        userService.existByEmailAndName(findPasswordReqDto.getEmail(), findPasswordReqDto.getName());
        // 1.1  예외처리
        // 2. authenticationEmail을 이용해 찾은 비밀번호을 보낸다.
        

//        return ResponseEntity.ok().body()
        return BaseResponseBodyUtiil.BaseResponseBodySuccess();
    }
}
