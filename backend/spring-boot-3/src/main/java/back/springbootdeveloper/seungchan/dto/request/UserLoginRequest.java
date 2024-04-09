package back.springbootdeveloper.seungchan.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoginRequest {

  @NotBlank(message = "{validation.email.notblank}")
  @Email(message = "{validation.email.invalid}")
  private String email;

  @NotBlank(message = "{validation.password.notblank}")
  private String password;
}
