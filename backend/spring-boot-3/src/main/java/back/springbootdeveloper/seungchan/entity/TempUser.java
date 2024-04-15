package back.springbootdeveloper.seungchan.entity;


import back.springbootdeveloper.seungchan.util.DayUtill;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "temp_user")
public class TempUser {

  @Id // id 필드를 기본키로 지정
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "name", length = 10, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String name;

  @Column(name = "phone_num", length = 20, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String phoneNum;

  @Column(name = "major", length = 20, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String major;

  @Column(name = "gpa", length = 10, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String gpa;

  @Column(name = "address", length = 100, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String address;

  @Column(name = "specialty_skill", length = 255, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String specialtySkill;

  @Column(name = "hobby", length = 255, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String hobby;

  @Column(name = "mbti", length = 10, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String mbti;

  @Column(name = "student_id", length = 10, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String studentId;

  @Column(name = "birth_date", length = 20, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String birthDate;

  @Column(name = "advantages", length = 1000, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String advantages;

  @Column(name = "disadvantage", length = 1000, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String disadvantage;

  @Column(name = "self_introduction", length = 1000, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String selfIntroduction;

  @Column(name = "photo", length = 255, nullable = false) // 'title'이라는 not null 컴럼과 매핑
  private String photo;

  @Column(name = "ob", nullable = false)
  private boolean isOb;

  @Column(name = "year_registration", length = 20, nullable = false)
  private String yearOfRegistration;

  @Column(name = "email", length = 40, nullable = false, unique = true)
  private String email;

  @Column(name = "password", length = 100, nullable = false)
  private String password;

  @Column(name = "regular_member", nullable = false)
  private boolean regularMember;

  @Column(name = "application_date", length = 20, nullable = false)
  private String applicationDate;

  @Builder
  public TempUser(String name, String phoneNum, String major, String gpa, String address,
      String specialtySkill, String hobby, String mbti, String studentId, String birthDate,
      String advantages, String disadvantage, String selfIntroduction, String photo, boolean isOb,
      String email, String password) {
    this.name = name;
    this.phoneNum = phoneNum;
    this.major = major;
    this.gpa = gpa;
    this.address = address;
    this.specialtySkill = specialtySkill;
    this.hobby = hobby;
    this.mbti = mbti;
    this.studentId = studentId;
    this.birthDate = birthDate;
    this.advantages = advantages;
    this.disadvantage = disadvantage;
    this.selfIntroduction = selfIntroduction;
    this.photo = photo;
    this.isOb = isOb;
    this.yearOfRegistration = DayUtill.getYear();
    this.email = email;
    this.password = password;
    this.regularMember = false;
    this.applicationDate = DayUtill.getStrYearMonthDay();
  }

  public void update(TempUser user) {
    this.id = user.getId();
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
  }
}

