package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoginRequest {
    @NotBlank(message = "{email.notnull}")
    @Email(message = "{email.invalid}")
    private String email;

    @NotBlank(message = "{password.notnull}")
    private String password;
}
