package back.springbootdeveloper.seungchan.filter.handler;

import back.springbootdeveloper.seungchan.Constant.filter.CustomHttpStatus;
import back.springbootdeveloper.seungchan.Constant.filter.exception.ExceptionMessage;
import back.springbootdeveloper.seungchan.filter.exception.ApiException;
import back.springbootdeveloper.seungchan.filter.exception.judgment.WeekendException;
import back.springbootdeveloper.seungchan.filter.exception.user.NewUserRegistrationException;
import back.springbootdeveloper.seungchan.filter.exception.user.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {UserNotExistException.class})
    public ResponseEntity<Object> handleUserNotExistException(UserNotExistException e) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                ExceptionMessage.USER_NOT_EXIST_MESSAGE.get(),
                httpStatus,
                httpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
//                ExceptionMessage.USER_NOT_EXIST_MESSAGE.get(),
                errorMessage,
                httpStatus,
                httpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {NewUserRegistrationException.class})
    public ResponseEntity<Object> handleNewUserRegistrationExceptionException(NewUserRegistrationException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                ExceptionMessage.NEW_USER_REGISTRATION_MESSAGE.get(),
                httpStatus,
                httpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {WeekendException.class})
    public ResponseEntity<Object> handleWeekendException(WeekendException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        CustomHttpStatus customHttpStatus = CustomHttpStatus.WEEKEND;

        ApiException apiException = new ApiException(
                ExceptionMessage.WEEKEND_MESSAGE.get(),
                httpStatus,
                customHttpStatus.value(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }
}
