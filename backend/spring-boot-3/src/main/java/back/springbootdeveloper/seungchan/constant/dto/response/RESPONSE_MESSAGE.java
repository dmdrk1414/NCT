package back.springbootdeveloper.seungchan.constant.dto.response;

public class RESPONSE_MESSAGE {

  public static String SUCCESE_TOGLE_MEMBER(final String userName) {
    return userName + "님을 일반 멤버로 변경되었습니다";
  }

  public static String SUCCESE_TOGLE_GRADUATION(final String userName) {
    return userName + "님을 졸업 멤버로 변경되었습니다";
  }

  public static String BAD_TOGGLE_MEMBER_GRADE(final String userName) {
    return userName + "님의 전환을 실패 하였습니다.";
  }

  public static String SUCCESE_GIVE_KING(final String targetUserName, final String userName) {
    return targetUserName + "에게 실장 권한을 드렸습니다. " + userName + "님은 일반 회원입니다";
  }
}
