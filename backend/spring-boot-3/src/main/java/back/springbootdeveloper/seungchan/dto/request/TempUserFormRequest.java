package back.springbootdeveloper.seungchan.dto.request;

import back.springbootdeveloper.seungchan.domain.TempUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TempUserFormRequest {
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
    private String password;

    public TempUser toEntity() {
        return TempUser.builder()
                .name(name)
                .phoneNum(phoneNum)
                .major(major)
                .gpa(gpa)
                .address(address)
                .specialtySkill(specialtySkill)
                .hobby(hobby)
                .mbti(mbti)
                .studentId(studentId)
                .birthDate(birthDate)
                .advantages(advantages)
                .disadvantage(disadvantage)
                .selfIntroduction(selfIntroduction)
                .photo(photo)
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .build();
    }
}
