package back.springbootdeveloper.seungchan.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewUsersResDto {
    private Long id;
    private String email;
    private String name;
    private String applicationDate;
}
