package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.request.UpdateEmailReqDto;
import back.springbootdeveloper.seungchan.dto.request.UpdatePasswordReqDto;
import back.springbootdeveloper.seungchan.entity.UserInfo;
import back.springbootdeveloper.seungchan.filter.exception.EmailSameMatchException;
import back.springbootdeveloper.seungchan.filter.exception.judgment.PasswordConfirmationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LookupService {
    private final TokenService tokenService;
    private final UserService userService;

    public void updatePassword(UpdatePasswordReqDto updatePasswordReqDto, HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);
        String updatePassword = updatePasswordReqDto.getUpdatePassword();
        String checkUpdatePassword = updatePasswordReqDto.getCheckUpdatePassword();

        if (checkPassword(updatePassword, checkUpdatePassword)) {
            UserInfo userInfo = userService.findUserById(userId);
            userService.updateUserPassword(userInfo.getEmail(), updatePassword);
        }
    }

    private Boolean checkPassword(String password, String checkPassword) {
        if (password.equals(checkPassword)) {
            return true;
        }
        throw new PasswordConfirmationException();
    }

    /**
     * 이메일을 업데이트한다. 현제 유저의 이메일과 변경을 원하는 이메일이 다르면
     * 이메일을 변경한다.
     *
     * @param updateEmail 변경 이메일
     * @param request
     */
    public void updateEmail(String updateEmail, HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);
        userService.updateEmail(userId, updateEmail);
    }
}
