package back.springbootdeveloper.seungchan.domain;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ObUser {

  private String name;
  private String yearOfRegistration;
  private String phoneNum;
  private Long userId;

  @Builder
  public ObUser(UserInfo user) {
    this.name = user.getName();
    this.yearOfRegistration = user.getYearOfRegistration();
    this.phoneNum = user.getPhoneNum();
    this.userId = user.getId();
  }
}
