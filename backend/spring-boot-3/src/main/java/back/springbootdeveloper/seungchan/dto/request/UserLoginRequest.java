package back.springbootdeveloper.seungchan.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoginRequest {
    private String email;
    private String password;
}
