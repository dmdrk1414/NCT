package back.springbootdeveloper.seungchan.constant.dto.response;

public enum ResponseMessage {
    TEMP_PASSWORD_MESSAGE("임시 비밀번호가 성공적으로 발급되었습니다. 이메일을 확인해주세요.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
