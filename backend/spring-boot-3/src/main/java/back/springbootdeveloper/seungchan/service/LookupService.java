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

    public void updateEmail(UpdateEmailReqDto updateEmailReqDto, HttpServletRequest request) {
        Long userId = tokenService.getUserIdFromToken(request);
        String email = updateEmailReqDto.getEmail();
        String updateEmail = updateEmailReqDto.getUpdateEmail();
        
        if (email.equals(updateEmail)) {
            throw new EmailSameMatchException();
        }

        userService.updateEmail(userId, updateEmailReqDto);
    }
}
