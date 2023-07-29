package back.springbootdeveloper.seungchan.domain;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ObUser {
    private String name;
    private String yearOfRegistration;
    private String phoneNum;

    @Builder
    public ObUser(User user) {
        this.name = user.getName();
        this.yearOfRegistration = user.getYearOfRegistration();
        this.phoneNum = user.getPhoneNum();
    }
}
