package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoginRequest {
    @NotNull(message = "이메일은 비어있는 값은 사용할 수 없습니다.")
    @Email
    private String email;

    @NotNull
    private String password;
}
