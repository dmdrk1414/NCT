package back.springbootdeveloper.seungchan.Constant.filter.exception;

import lombok.Getter;

public enum ExceptionMessage {
    EMPTY_VALUE_EXCEPTION("양식에 빈 값이 있습니다."),
    PASSWORD_NOT_MATCH_MESSAGE("비밀번호가 일치하지 않습니다."),
    USER_ALREADY_EXIST_MESSAGE("동일한 이메일을 가진 유저가 이미 존재합니다."),
    USER_NOT_EXIST_MESSAGE("유저가 존재하지 않습니다."),
    LOG_IN_FAIL_MESSAGE("로그인에 실패하였습니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
