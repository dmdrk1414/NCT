package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.domain.User;
import back.springbootdeveloper.seungchan.domain.UserUtill;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginResponse {
    private String accessToken;
    private String name;
    private Long userId;
    private boolean isNuriKing;

    public UserLoginResponse(String accessToken, User user, UserUtill userUtill) {
        this.accessToken = accessToken;
        this.name = user.getName();
        this.userId = user.getId();
        this.isNuriKing = userUtill.isNuriKing();
    }
}
