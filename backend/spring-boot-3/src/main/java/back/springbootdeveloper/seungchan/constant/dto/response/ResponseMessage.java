package back.springbootdeveloper.seungchan.constant.dto.response;

import java.lang.annotation.Annotation;
import org.hibernate.validator.cfg.ConstraintDef;

public enum ResponseMessage {
  TEMP_PASSWORD_MESSAGE("임시 비밀번호가 성공적으로 발급되었습니다. 이메일을 확인해주세요."),
  UPDATE_PASSWORD_MESSAGE("비밀번호가 성공적으로 업데이트되었습니다."),
  UPDATE_EMAIL_MESSAGE("이메일이 성공적으로 업데이트 되었습니다."),
  FIND_EMAIL_OK("이메일 주소 찾기가 완료되었습니다. 이메일을 확인해주세요."),
  BAD_IS_NURI_KING("실장을 대상으로 할 수 없는 기능 입니다."),
  SUCCESS_DELETE_USER("성공적으로 추방 하였습니다."),
  BAD_IS_GRADUATION_USER("졸업 인원은 추방 할 수 없습니다."),
  BAD_NOT_GIVE_KING_GRADUATION_USER("졸업 인원은 실장 권한을 줄수 없습니다."),
  BAD_NOT_USER_NOMAL("일반 회원은 사용할 수 없습니다."),
  SUCCESS_WRITE_NOTICE("공지 사항 등록 완료했습니다."),
  SUCCESS_DELETE_NOTICE("공지 사항 삭제 완료 했습니다."),
  BAD_DELETE_NOTICE("해당 공지 사항을 삭제 할 수 없습니다.");

  private final String message;

  ResponseMessage(String message) {
    this.message = message;
  }

  public String get() {
    return message;
  }
}
