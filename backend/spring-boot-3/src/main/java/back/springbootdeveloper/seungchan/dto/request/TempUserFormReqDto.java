package back.springbootdeveloper.seungchan.dto.request;

import back.springbootdeveloper.seungchan.entity.TempUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "이름은 비어 있을 수 없습니다.")
    private String name;

    @NotBlank(message = "전화번호는 비어 있을 수 없습니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "유효하지 않은 전화번호 형식입니다.")
    private String phoneNum;

    @NotBlank(message = "전공은 비어 있을 수 없습니다.")
    private String major;

    @NotBlank(message = "GPA는 비어 있을 수 없습니다.")
    private String gpa;

    @NotBlank(message = "주소는 비어 있을 수 없습니다.")
    private String address;

    @NotBlank(message = "전문 기술은 비어 있을 수 없습니다.")
    private String specialtySkill;

    @NotBlank(message = "취미는 비어 있을 수 없습니다.")
    private String hobby;

    @NotBlank(message = "MBTI는 비어 있을 수 없습니다.")
    private String mbti;

    @NotBlank(message = "학생 ID는 비어 있을 수 없습니다.")
    private String studentId;

    @NotBlank(message = "생년월일은 비어 있을 수 없습니다.")
    private String birthDate;

    @NotBlank(message = "장점은 비어 있을 수 없습니다.")
    private String advantages;

    @NotBlank(message = "단점은 비어 있을 수 없습니다.")
    private String disadvantage;

    @NotBlank(message = "자기소개는 비어 있을 수 없습니다.")
    private String selfIntroduction;

    @NotBlank(message = "사진은 비어 있을 수 없습니다.")
    private String photo;

    @Email(message = "유효하지 않은 이메일 형식입니다.")
    @NotBlank(message = "이메일은 비어 있을 수 없습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 최소 8자 이상이며, 문자, 숫자, 특수문자를 각각 하나 이상 포함해야 합니다.")
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
