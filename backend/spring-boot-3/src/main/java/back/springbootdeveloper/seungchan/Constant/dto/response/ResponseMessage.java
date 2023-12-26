package back.springbootdeveloper.seungchan.Constant.dto.response;

public enum ResponseMessage {
    LOG_IN_FAIL_MESSAGE("로그인에 실패하였습니다. 다시 시도해주세요.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
