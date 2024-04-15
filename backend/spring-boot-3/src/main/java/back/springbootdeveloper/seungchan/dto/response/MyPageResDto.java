package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MyPageResDto {

  private String name;
  private String phoneNum;
  private String major;
  private String gpa;
  private String address;
  private String specialtySkill;
  private String hobby;
  private String mbti;
  private String studentId;
  private String birthDate;
  private String advantages;
  private String disadvantage;
  private String selfIntroduction;
  private String photo;
  private String email;
  private boolean isOb;
  private String yearOfRegistration;

  public MyPageResDto(UserInfo user) {
    this.name = user.getName();
    this.phoneNum = user.getPhoneNum();
    this.major = user.getMajor();
    this.gpa = user.getGpa();
    this.address = user.getAddress();
    this.specialtySkill = user.getSpecialtySkill();
    this.hobby = user.getHobby();
    this.mbti = user.getMbti();
    this.studentId = user.getStudentId();
    this.birthDate = user.getBirthDate();
    this.advantages = user.getAdvantages();
    this.disadvantage = user.getDisadvantage();
    this.selfIntroduction = user.getSelfIntroduction();
    this.photo = user.getPhoto();
    this.isOb = user.isOb();
    this.yearOfRegistration = user.getYearOfRegistration();
    this.email = user.getEmail();
  }
}
