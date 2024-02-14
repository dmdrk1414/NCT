package back.springbootdeveloper.seungchan.constant.dto.response;

public enum ResponseMessage {
    TEMP_PASSWORD_MESSAGE("임시 비밀번호가 성공적으로 발급되었습니다. 이메일을 확인해주세요."),
    UPDATE_PASSWORD_MESSAGE("비밀번호가 성공적으로 업데이트되었습니다."),
    UPDATE_EMAIL_MESSAGE("이메일이 성공적으로 업데이트 되었습니다."),
    FIND_EMAIL_OK("이메일 주소 찾기가 완료되었습니다. 이메일을 확인해주세요."),
    UPDATE_TEAM_ARTICLE_MESSAGE("게시글 수정에 되였습니다.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
