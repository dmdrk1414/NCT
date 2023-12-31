package back.springbootdeveloper.seungchan.constant.filter.exception;

public enum ExceptionMessage {
    USER_NOT_EXIST_MESSAGE("해당 사용자가 존재하지 않습니다."),
    NEW_USER_REGISTRATION_MESSAGE("신입 유저 가입에 실패하였습니다. 다시 시도해주세요."),
    WEEKEND_MESSAGE("주말은 출석 할 수 없습니다."),
    PASSWORD_CONFIRMATION("비밀번호와 비밀번호 확인란의 내용이 일치해야 합니다.");


    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
