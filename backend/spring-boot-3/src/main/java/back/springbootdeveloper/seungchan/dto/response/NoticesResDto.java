package back.springbootdeveloper.seungchan.dto.response;

import back.springbootdeveloper.seungchan.entity.Notice;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NoticesResDto {

  List<NoticeInformation> noticeInformations;
}
