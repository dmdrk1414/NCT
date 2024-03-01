package back.springbootdeveloper.seungchan.constant.regexp;

/**
 * 정규식을 관리하는 enum
 */
public enum RegexpConstant {
  PASSWORD("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");

  private String regexp;

  RegexpConstant(String regexp) {
    this.regexp = regexp;
  }

  public String get() {
    return regexp;
  }
}
