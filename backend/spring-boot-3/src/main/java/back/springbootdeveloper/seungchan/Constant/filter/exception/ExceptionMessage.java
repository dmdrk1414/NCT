package back.springbootdeveloper.seungchan.Constant.filter.exception;

import lombok.Getter;

public enum ExceptionMessage {
    USER_NOT_EXIST_MESSAGE("해당 사용자가 존재하지 않습니다.");


    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
