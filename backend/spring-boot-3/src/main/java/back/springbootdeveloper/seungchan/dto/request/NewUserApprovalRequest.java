package back.springbootdeveloper.seungchan.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewUserApprovalRequest {
    private String email;
}
