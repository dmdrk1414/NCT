package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.domain.YbUserInfomation;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YbUserListResponse {
    private List<YbUserInfomation> ybUserInfomationList;
    private boolean isPassAttendanceOfSearchUse;
}
