package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.domain.ObUser;
import back.springbootdeveloper.seungchan.domain.TempUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUsersResponse {
    private String email;
    private String name;
    private String applicationDate;
}
