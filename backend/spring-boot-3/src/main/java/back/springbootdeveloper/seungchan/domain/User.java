package back.springbootdeveloper.seungchan.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String name;

    @Column(name = "phoneNum", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String phoneNum;

    @Column(name = "major", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String major;

    @Column(name = "gpa", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String gpa;

    @Column(name = "address", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String address;


    @Column(name = "specialtySkill", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String specialtySkill;

    @Column(name = "hobby", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String hobby;

    @Column(name = "mbti", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String mbti;

    @Column(name = "studentId", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String studentId;

    @Column(name = "birthDate", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String birthDate;

    @Column(name = "advantages", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String advantages;

    @Column(name = "disadvantage", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String disadvantage;

    @Column(name = "selfIntroduction", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String selfIntroduction;

    @Column(name = "photo", nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String photo;

    @Column(name = "is_ob", nullable = false)
    private boolean isOb;

    @Column(name = "year_registration", nullable = false)
    private String yearOfRegistration;

    @Builder
    public User(String name, String phoneNum, String major, String gpa, String address, String specialtySkill, String hobby, String mbti, String studentId, String birthDate, String advantages, String disadvantage, String selfIntroduction, String photo, boolean isOb, String yearOfRegistration) {
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
        this.yearOfRegistration = yearOfRegistration;
    }

    public void update(User user) {
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

