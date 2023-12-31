package back.springbootdeveloper.seungchan.controller;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.request.FindPasswordReqDto;
import back.springbootdeveloper.seungchan.dto.request.UpdateEmailReqDto;
import back.springbootdeveloper.seungchan.dto.request.UpdatePasswordReqDto;
import back.springbootdeveloper.seungchan.dto.response.BaseResponseBody;
import back.springbootdeveloper.seungchan.filter.exception.EmailSameMatchException;
import back.springbootdeveloper.seungchan.service.AuthenticationEmailService;
import back.springbootdeveloper.seungchan.service.LookupService;
import back.springbootdeveloper.seungchan.service.UserService;
import back.springbootdeveloper.seungchan.util.BaseResponseBodyUtiil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    private final LookupService lookupService;

    @Operation(summary = "admin page 비밀 번호 찾기 api ", description = "이메일 인증을 통해 해당 이메일로 비밀번호를 보낸다.")
    @PostMapping("/find/password")
    public ResponseEntity<BaseResponseBody> getTempPasswordController(@RequestBody @Valid FindPasswordReqDto findPasswordReqDto) {
        String email = findPasswordReqDto.getEmail();

        userService.existByEmailAndName(email, findPasswordReqDto.getName());
        String tempPassword = authenticationEmailService.sendSimpleMessage(findPasswordReqDto.getAuthenticationEmail(), email);
        userService.updateUserPassword(email, tempPassword);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.TEMP_PASSWORD_MESSAGE.get());
    }

    @Operation(summary = "admin page 비밀 변경하기 api", description = "해당 유저가 비밀번호를 변경한다.")
    @PostMapping("/update/password")
    public ResponseEntity<BaseResponseBody> updatePasswordController(@RequestBody @Valid UpdatePasswordReqDto updatePasswordReqDto, HttpServletRequest request) {
        lookupService.updatePassword(updatePasswordReqDto, request);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.UPDATE_PASSWORD_MESSAGE.get());
    }

    @Operation(summary = "admin page 이메일 변경하기 api", description = "해당 유저가 이메일를 변경한다.")
    @PostMapping("/update/email")
    public ResponseEntity<BaseResponseBody> updateEmailController(@RequestBody @Valid UpdateEmailReqDto updateEmailReqDto, HttpServletRequest request) {
        String email = updateEmailReqDto.getEmail();
        String updateEmail = updateEmailReqDto.getUpdateEmail();

        if (email.equals(updateEmail)) {
            throw new EmailSameMatchException();
        }

        lookupService.updateEmail(updateEmail, request);

        return BaseResponseBodyUtiil.BaseResponseBodySuccess(ResponseMessage.UPDATE_PASSWORD_MESSAGE.get());
    }
}
