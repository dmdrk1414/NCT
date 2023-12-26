package back.springbootdeveloper.seungchan.Constant.filter.exception;

import lombok.Getter;

public enum ExceptionMessage {
    USER_NOT_EXIST_MESSAGE("해당 사용자가 존재하지 않습니다."),
    LOG_IN_FAIL_MESSAGE("로그인에 실패하였습니다. 다시 시도해주세요.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
