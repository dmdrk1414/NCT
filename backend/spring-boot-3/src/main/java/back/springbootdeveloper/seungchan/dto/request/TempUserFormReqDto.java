package back.springbootdeveloper.seungchan.dto.request;

import back.springbootdeveloper.seungchan.entity.TempUser;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TempUserFormReqDto {
    @NotBlank(message = "{validation.name.notblank}")
    private String name;

    @NotBlank(message = "{validation.phonenum.notblank}")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "{validation.phonenum.invalid}")
    private String phoneNum;

    @NotBlank(message = "{validation.major.notblank}")
    private String major;

    @NotBlank(message = "{validation.gpa.notblank}")
    private String gpa;

    @NotBlank(message = "{validation.address.notblank}")
    private String address;

    @NotBlank(message = "{validation.specialtyskill.notblank}")
    private String specialtySkill;

    @NotBlank(message = "{validation.hobby.notblank}")
    private String hobby;

    @NotBlank(message = "{validation.mbti.notblank}")
    private String mbti;

    @NotBlank(message = "{validation.studentid.notblank}")
    private String studentId;

    @NotBlank(message = "{validation.birthdate.notblank}")
    private String birthDate;

    @NotBlank(message = "{validation.advantages.notblank}")
    @Size(min = 100, message = "{validation.advantages.size.min.100}")
    private String advantages;

    @NotBlank(message = "{validation.disadvantage.notblank}")
    @Size(min = 100, message = "{validation.disadvantage.size.min.100}")
    private String disadvantage;

    @NotBlank(message = "{validation.selfintroduction.notblank}")
    @Size(min = 200, message = "{validation.selfintroduction.size.min.200}")
    private String selfIntroduction;

    @NotBlank(message = "{validation.photo.notblank}")
    private String photo;

    @Email(message = "{validation.email.invalid}")
    @NotBlank(message = "{validation.email.notblank}")
    private String email;

    @NotBlank(message = "{validation.password.notblank}")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "{validation.password.invalid}")
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
