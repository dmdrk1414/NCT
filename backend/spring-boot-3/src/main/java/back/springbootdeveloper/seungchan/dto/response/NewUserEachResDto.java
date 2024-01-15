package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.TempUser;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewUserEachResDto {
    private TempUser tempUser;
    private boolean isNuriKing;
}
