package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.domain.TempUser;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewUserEachResponse {
    private TempUser tempUser;
    private boolean isNuriKing;
}
