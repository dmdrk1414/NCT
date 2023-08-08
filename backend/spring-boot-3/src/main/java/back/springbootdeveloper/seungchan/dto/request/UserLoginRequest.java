package back.springbootdeveloper.seungchan.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoginRequest {
    private String email;
    private String password;
}
